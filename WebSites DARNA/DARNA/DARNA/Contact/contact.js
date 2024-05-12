function sendMail(){
    var params = {
        name : document.getElementById("name").value ,
        email : document.getElementById("email").value ,
        message : document.getElementById("message").value ,
    };
    const serviceID = "service_bcus0j5" ;
const templateID = "template_eg587aw" ;

emailjs.send(serviceID,templateID,params) 
.then((res) => {
    document.getElementById("name").value = "" ;
    document.getElementById("email").value = "" ;

    document.getElementById("message").value = "" ;

    console.log(res);
    alert("Your message sent successfully") ;

})
.catch((err) => console.log(err)) ; 

}


