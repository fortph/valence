$(document).ready(function(){$.datepicker.regional["fr"]={closeText:"Fermer",prevText:"Precedent",nextText:"Suivant",currentText:"Aujourd'hui",monthNames:["janvier","fevrier","mars","avril","mai","juin","juillet","aout","septembre","octobre","novembre","decembre"],monthNamesShort:["janv.","fevr.","mars","avril","mai","juin","juil.","aout","sept.","oct.","nov.","dec."],dayNames:["dimanche","lundi","mardi","mercredi","jeudi","vendredi","samedi"],dayNamesShort:["dim.","lun.","mar.","mer.","jeu.","ven.","sam."],dayNamesMin:["D","L","M","M","J","V","S"],weekHeader:"Sem.",dateFormat:"dd-mm-yy",firstDay:1,isRTL:false,showMonthAfterYear:true,yearRange:"2014:2030",yearSuffix:""};$.datepicker.setDefaults($.datepicker.regional["fr"]);$("#datedebut").datepicker({changeMonth:true,changeYear:true});$("#datefin").datepicker({changeMonth:true,changeYear:true});});