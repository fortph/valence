function recupererSaisie(){var code=$("#cp").val();if(code.length>2){CodesPostaux.afficherListe(code,remplirListeValeurs);}}function remplirListeValeurs(listevilles){dwr.util.removeAllOptions("ville");dwr.util.addOptions("ville",listevilles);}function recupererEmployeur(){Employeur.afficherEmployeurs(remplirListeEmployeurs);}function remplirListeEmployeurs(listeemp){dwr.util.removeAllOptions("employeurs");dwr.util.addOptions("employeurs",listeemp);}function recupererContacts(){var emp=new String($("#employeurs").val());var total=emp.length;var sup=emp.lastIndexOf("-");var intsup=parseInt(sup);intsup=intsup+1;var reste=emp.substring(intsup,total);Contact.afficherNomContacts(reste,remplirListeContacts);}function remplirListeContacts(listcont){dwr.util.removeAllOptions("contacts");dwr.util.addOptions("contacts",listcont);}function recupererServices(){var reste=$("#employeurs").val();var code=reste.lastIndexOf("-");reste=reste.substring(code+1);Service.afficherServices(reste,remplirListeServices);}function remplirListeServices(listserv){dwr.util.removeAllOptions("services");dwr.util.addOptions("services",listserv);}function recupererActivite(){Activite.afficherActivites(remplirListeActivitesEmployeurs);}function remplirListeActivitesEmployeurs(listeact){dwr.util.removeAllOptions("activite");dwr.util.addOptions("activite"," ");dwr.util.addOptions("activite",listeact);}function afficherStatuts(){Statut.afficherStatuts(remplirListeStatut);}function remplirListeStatut(listestat){dwr.util.removeAllOptions("statut");dwr.util.addOptions("statut",listestat);}function afficherStructure(){Structure.afficherStructure(remplirListeStructure);}function remplirListeStructure(listestruct){dwr.util.removeAllOptions("structure");dwr.util.addOptions("structure"," ");dwr.util.addOptions("structure",listestruct);}function afficheCapUtilisateurs(){Utilisateur.afficherUtilisateurs(remplirListeUtil);}function remplirListeUtil(listestruct){dwr.util.removeAllOptions("utilisateurs");dwr.util.addOptions("utilisateurs"," ");dwr.util.addOptions("utilisateurs",listestruct);}function recupererEmploiPropose(){Rome.afficherRome(remplirListeRome1);}function remplirListeRome1(liste1){var tab=new String(liste1);var table=tab.split(",");var j=1;for(var i=0;i<table.length;i++){if(table[i]!=table[i].toUpperCase()){if(table[i].search("/")==-1){table[i]=j+". "+table[i];j=j+1;}else{j=1;}}}dwr.util.addOptions("emploipropose",table,{escapeHtml:true});}function recupererCivilite(){Civilite.afficherCivilites(remplirListeCivilites);}function remplirListeCivilites(listecivil){dwr.util.removeAllOptions("civiliteemp");dwr.util.addOptions("civiliteemp"," ");dwr.util.addOptions("civiliteemp",listecivil);}function recupererCiviliteResp(){Civilite.afficherCivilites(remplirListeCivilitesResp);}function remplirListeCivilitesResp(listecivil){dwr.util.removeAllOptions("civiliteresp");dwr.util.addOptions("civiliteresp"," ");dwr.util.addOptions("civiliteresp",listecivil);}function recupererCiviliteCont(){Civilite.afficherCivilites(remplirListeCivilitesCont);}function remplirListeCivilitesCont(listecivil){dwr.util.removeAllOptions("civilitecontact");dwr.util.addOptions("civilitecontact"," ");dwr.util.addOptions("civilitecontact",listecivil);}function recupererinfoscaputilisateur(){var utilisateur=$("#utilisateurs").val();Utilisateur.findByName(utilisateur,recupereInfoscap);}function recupereInfoscap(db){if(db!=null){dwr.util.setValue("login",db.login);dwr.util.setValue("ancienlogin",db.id_salarie);dwr.util.setValue("privilege",db.privilege);dwr.util.setValue("mail",db.mail);if(db.actif){dwr.util.setValue("oui",true);dwr.util.setValue("non",false);}else{dwr.util.setValue("oui",false);dwr.util.setValue("non",true);}}else{db={};}}