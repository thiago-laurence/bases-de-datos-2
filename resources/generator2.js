for (var i = 1; i <= 50000; i++) {
    db.stops.insert({
        name:'Stop '+i,
        code: i,
        description: 'Description of stop '+i,
    });
}
for (var i = 1; i <= 50000; i++) {
    var randomLong = -34.56 - (Math.random() * .23);
    var randomLat = -58.4 - (Math.random() * .22);
    var price = Math.floor(Math.random() * 901) + 100;
    db.routes.insert({
        name:'Route '+i,
        price: price,
        startPoint: {
                type: "Point",
                coordinates: [randomLat, randomLong]
            }
    });
}
    