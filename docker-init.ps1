mkdir -Force -p ~/docker/data
mkdir -Force -p ~/docker/config
mkdir -Force -p ~/docker/pgsql

docker run -p 9000:9000 -p 9001:9001 --name local-minio `
  -e "MINIO_ACCESS_KEY=minio" `
  -e "MINIO_SECRET_KEY=wLiIgRc%qiC7~9qca6^" `
  -v ~/docker/data:/data `
  -v ~/docker/config:/root/.minio `
  -d minio/minio server /data --console-address ":9001"

docker run -p 5432:5432 --name local-postgres `
 -e "POSTGRES_PASSWORD=FOzVhNc)LIz6b3^Pw~" `
 -e "PGDATA=/var/lib/postgresql/data/pgdata" `
 -v ~/docker/pgsql:/var/lib/postgresql/data `
 -d postgres