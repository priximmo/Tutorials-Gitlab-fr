#!/bin/bash


DIR="${HOME}/generator"
USER_SCRIPT=$USER

# Fonctions ###########################################################

help_list() {
  echo "Usage:

  ${0##*/} [-h][--postgres]

Options:

  -h, --help
    can I help you ?

  -i, --ip
    list ip for each container

  -p, --postgres [ID if you want]
    run postgres 

  --clean [container name]
		remove container and datas
  "
}


ip() {
for i in $(docker ps -q); do docker inspect -f "{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}} - {{.Name}}" $i;done
}

postgres() {

echo
echo "Install Postgres"
echo ""
[ ! -z $1 ] && echo "Création du conteneur : postgres${1}" && ID_CONTENEUR=$1 && echo "" 
echo "1 - Create directories ${DIR}/generator/postgres${ID_CONTENEUR}/"
mkdir -p $DIR/postgres${ID_CONTENEUR}/

echo "
version: '3.0'
services:
  postgres${ID_CONTENEUR}:
   image: postgres:latest
   container_name: postgres${ID_CONTENEUR}
   environment:
   - POSTGRES_USER=myuser
   - POSTGRES_PASSWORD=password
   - POSTGRES_DB=mydb
   expose:
   - 5432
#   volumes:
#   - postgres_data${ID_CONTENEUR}:/var/lib/postgresql/data/
   networks:
   - generator     
#volumes:
#  postgres_data${ID_CONTENEUR}:
#    driver: local
#    driver_opts:
#      o: bind
#      type: none
#      device: ${DIR}/postgres${ID_CONTENEUR}
networks:
  generator:
   driver: bridge
   ipam:
     config:
       - subnet: 192.168.168.0/24
" >$DIR/docker-compose-postgres${ID_CONTENEUR}.yml

echo "2 - Run postgres"
docker-compose -f $DIR/docker-compose-postgres${ID_CONTENEUR}.yml up -d

echo "
Credentials:
		user: myuser
		password: password
		db: mydb
		port: 5432

command : psql -h <ip> -u myuser mydb

"

}

clean(){
NAME_CONTENEUR=$1
[ -z ${NAME_CONTENEUR} ] && exit 1
docker-compose -f $DIR/docker-compose-${NAME_CONTENEUR}.yml down
#[ ! -z ${NAME_CONTENEUR} ] && rm -rf $DIR/${NAME_CONTENEUR}
rm -f $DIR/docker-compose-postgres${ID_CONTENEUR}.yml
docker volume prune -f
}

## Execute ########################################################


optspec=":ihvp-:"
while getopts "$optspec" optchar; do
    case "${optchar}" in
        -)
            case "${OPTARG}" in
                postgres)
                    arg="${!OPTIND}"; OPTIND=$(( $OPTIND + 1 ))
                    postgres "$arg"
                    ;;
								clean)
                    arg="${!OPTIND}"; OPTIND=$(( $OPTIND + 1 ))
                    clean "$arg"
                    ;;
								ip)
										ip ;;
        				help)	echo "Erreur reportez-vous à l'aide"
											help_list ;;
        				*)		echo "Erreur reportez-vous à l'aide"
											help_list ;;
            		esac;;
				i)	ip ;;
				p)	postgres ;;
        h)	help_list ;;
        *)	echo "Erreur reportez-vous à l'aide"
						help_list ;;
    esac
done
