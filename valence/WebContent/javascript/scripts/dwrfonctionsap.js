var salairehoraire;var panier;var deplace;var mini;function recupererTacheSAP(){TachesSAP.afficherListeTaches(remplirListeTaches);}function remplirListeTaches(listetaches){dwr.util.removeAllOptions("tachepropose");dwr.util.addOptions("tachepropose"," ");dwr.util.addOptions("tachepropose",listetaches);}function recupererSalaire(){var salarie=$("#personne").val();var pos=salarie.lastIndexOf("(");salarie=salarie.substring(pos+1,salarie.length-1);ContratCDI.dernierContrat(parseInt(salarie),rechercheSalaire);}function recupererSalaire1(id){ContratCDI.dernierContrat(id,rechercheSalaire);}function rechercheSalaire(salaire){salairehoraire=salaire.salairehoraire;panier=salaire.panier;deplace=salaire.deplacement;mini=salaire.heuresminimois;var cont=salaire.id_contratcdi;AvenantCDI.dernierAvenantContratCDIJavascript(parseInt(cont),rechercheAvenantSalaire);dwr.util.setValue("salhor",salairehoraire);dwr.util.setValue("panier",panier);dwr.util.setValue("deplacement",deplace);dwr.util.setValue("heuresmini",mini);return false;}function rechercheAvenantSalaire(contrat){if(contrat!=undefined){salairehoraire=contrat.salairehoraire;panier=contrat.panier;deplace=contrat.deplacement;mini=contrat.heuresminimois;dwr.util.setValue("salhor",salairehoraire);dwr.util.setValue("panier",panier);dwr.util.setValue("deplacement",deplace);dwr.util.setValue("heuresmini",mini);}}