docker run -p 9000:9000 -p 9001:9001 --name minio `
  -e "MINIO_ACCESS_KEY=minio" `
  -e "MINIO_SECRET_KEY=MINIO@local" `
  -d minio/minio server /data --console-address ":9001"

docker run -p 5432:5432 --name postgres `
 -e "POSTGRES_PASSWORD=POSTGRES@local" `
 -d postgres:13