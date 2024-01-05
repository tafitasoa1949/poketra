$(document).ready(function(){
    $('#afficher_graphe_voyage').click(function(event){
        event.preventDefault();

        var idmembre = document.getElementById('idchauffeurVoyage').value;
        var mois = document.getElementById('moisChoisit').value;
        var annee = document.getElementById('anneeChoisit').value;

        var route =  "http://localhost:8000/grapheVoyage/"+idmembre+"/"+mois+"/"+annee+"";
        console.log("Route >>> "+route);

        $.ajax({
            url: route,
            type: "GET",
            success: function(response){
                var jours = response.jours;

                var voyages = response.nbVoyages;
                var graphe_nbvoyage = c3.generate({
                    bindto: '#graphe_nbvoyage',
                    data:{
                        x: 'x',
                        columns:[
                            ['x'].concat(jours),
                            ['Nombre de voyage'].concat(voyages)
                        ],
                        type: 'area-spline'
                    },
                    axis: {
                        x: {
                            type: 'category'
                        },

                        y: {
                            min: 0,
                            max: 5,
                            tick: {
                                count: 6,
                                values: [0,1,2,3,4,5]
                            }
                        }
                    }
                });

                var gainVoyages = response.gainsVoyages;
                var graphe_gainsvoyage = c3.generate({
                    bindto: '#graphe_gainsvoyage',
                    data:{
                        x: 'x',
                        columns:[
                            ['x'].concat(jours),
                            ['Revenu'].concat(gainVoyages)
                        ],
                        type: 'area-spline'
                    },
                    axis: {
                        x: {
                            type: 'category'
                        },

                        y: {
                            min: 0
                        }
                    }
                });

            },
            error: function(xhr,status,error){
                console.log(error);
            }
        });
    });

    $('#afficher_graphe_revenue').click(function(event){
        event.preventDefault();

        var mois = document.getElementById('moisChoisit').value;
        var annee = document.getElementById('anneeChoisit').value;

        var route =  "http://localhost:8000/grapheRevenu/"+mois+"/"+annee;
        console.log("Route >>> "+route);

        var moisactuel = document.getElementById('moisactuel');
        var revenumensuel = document.getElementById('revenumensuel');

        $.ajax({
            url: route,
            type: "GET",
            success: function(response){
                revenumensuel.innerText = response.revmens.toLocaleString() + " Ariary";
                moisactuel.innerText = "mois de "+response.moisLettre+" "+annee;
                var jours = response.jours;

                var nbvoyagesParJours = response.nbvoyagesParJours;
                var graphe_nbvoyage = c3.generate({
                    bindto: '#graphe_nbvoyages',
                    data:{
                        x: 'x',
                        columns:[
                            ['x'].concat(jours),
                            ['Nombre de voyage'].concat(nbvoyagesParJours)
                        ],
                        type: 'area-spline'
                    },
                    axis: {
                        x: {
                            type: 'category'
                        },

                        y: {
                            min: 0,
                            max: 5,
                            tick: {
                                count: 6,
                                values: [0,1,2,3,4,5]
                            }
                        }
                    }
                });

                var gainsParJours = response.gainsParJours;
                var graphe_gainsvoyage = c3.generate({
                    bindto: '#graphe_revenuvoyages',
                    data:{
                        x: 'x',
                        columns:[
                            ['x'].concat(jours),
                            ['Revenu'].concat(gainsParJours)
                        ],
                        type: 'area-spline'
                    },
                    axis: {
                        x: {
                            type: 'category'
                        },

                        y: {
                            min: 0
                        }
                    }
                });
            },
            error: function(xhr,status,error){
                console.log(error);
            }
        });
    });

    $('#afficher_graphe_vehicule').click(function(event) {
        event.preventDefault();

        var idbus = document.getElementById('idvehicule').value;
        var mois = document.getElementById('moisChoisit').value;
        var annee = document.getElementById('anneeChoisit').value;

        var route =  "http://localhost:8000/graphVehicule/"+idbus+"/"+mois+"/"+annee;
        console.log("Route >>> "+route);

        $.ajax({
            url: route,
            type: "GET",
            success: function(response){
                var moisLettre = response.moisLettre;
                var jours = response.jours;
                var totalVoyages = response.totalVoyages;
                var listesNbVoyages = response.listesNbVoyages;

                var afficheVoyageMois = document.getElementById('nbVoyageParMois');
                afficheVoyageMois.innerHTML = "Nombre de voyage mois de "+moisLettre+" "+annee+" : "+"<b>"+totalVoyages+"</b>";

                var graphe_vehicule = c3.generate({
                    bindto: '#graphe_vehiculeVoyage',
                    data:{
                        x: 'x',
                        columns:[
                            ['x'].concat(jours),
                            ['Nombre de voyage'].concat(listesNbVoyages)
                        ],
                        type: 'area-spline'
                    },
                    axis: {
                        x: {
                            type: 'category'
                        },

                        y: {
                            min: 0,
                            max: 5,
                            tick: {
                                count: 6,
                                values: [0,1,2,3,4,5]
                            }
                        }
                    }
                });
            },
            error: function(xhr,status,error){
                console.log(error);
            }
        });
    })
});
