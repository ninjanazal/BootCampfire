const db_name = process.env.MONGO_AUTH_NAME;
const db_user = process.env.MONGO_AUTH_USER;
const db_password = process.env.MONGO_AUTH_PASSWORD;

db = db.getSiblingDB(db_name);
db.createUser({
	user: db_user,
	pwd: db_password,
	roles: [{ role: "dbOwner", db: db_name }]
});