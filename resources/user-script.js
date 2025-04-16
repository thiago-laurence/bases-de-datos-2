db = db.getSiblingDB('bd2_tours_5');

db.createUser({
    user: 'AdminGrupo5',
    pwd: 'AdminGrupo5Password',
    roles: [
        {
            role: 'dbOwner',
            db: 'bd2_tours_5',
        },
    ],
});