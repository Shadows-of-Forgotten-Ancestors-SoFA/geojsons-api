const fs = require('fs');
const path = '/docker-entrypoint-initdb.d/geojsons/';
const collectionName = 'geojsons';
const dbName = 'test';

db = db.getSiblingDB(dbName);

fs.readdirSync(path).forEach(file => {
    if (file.endsWith('.geojson')) {
        const data = JSON.parse(fs.readFileSync(path + file, 'utf8'));
        db[collectionName].insert(data);
    }
});