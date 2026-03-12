# Lab 6: SOAP and JSON Service Integration

## Төслийн товч танилцуулга

Тухайн лабораторийн ажлаар SOAP service болон JSON REST service хооронд интеграц хийж, хэрэглэгчийн бүртгэл, нэвтрэлт, profile удирдлагын урсгалыг хэрэгжүүлсэн. Систем нь Frontend App, SOAP Auth Service, JSON User Service гэсэн 3 үндсэн хэсгээс бүрдэнэ.

SOAP Auth Service нь хэрэглэгч бүртгэх, нэвтрэх, token шалгах ажиллагааг хариуцдаг. JSON User Service нь profile дээр CRUD үйлдэл хийх үүрэгтэй бөгөөд authentication logic-ийг өөрөө хийхгүй, SOAP service рүү `ValidateToken` хүсэлт илгээж баталгаажуулсны дараа profile мэдээлэлд ханддаг.

---

## Ажиллуулах заавар

### Алхам 1. SOAP Auth Service ажиллуулах

`user-soap-service` төслийг Eclipse дээр нээж, project дээр right click хийн **Run As -> Spring Boot App** сонголтоор ажиллуулна.

SOAP service ассан эсэхийг дараах хаягаар шалгаж болно.

`http://localhost:8080/ws/auth.wsdl`

### Алхам 2. JSON User Service ажиллуулах

`user-json-service` төслийг Eclipse дээр нээж, project дээр right click хийн **Run As -> Spring Boot App** сонголтоор ажиллуулна.

Энэ service нь `application.properties` файлд `8081` порт дээр тохируулагдсан.

JSON service ажиллаж байгаа үед profile CRUD хүсэлтүүдийг дараах үндсэн хаягаар дуудах боломжтой.

`http://localhost:8081/users`

### Алхам 3. Frontend App ажиллуулах

`frontend-app` folder-ыг VS Code дээр нээж **Live Server** ашиглан ажиллуулна.

Жишээ хаяг:

`http://127.0.0.1:5500/login.html`

Frontend ажиллахын тулд SOAP service болон JSON service хоёулаа ассан байх шаардлагатай.

---

## Архитектур

Системийн архитектур нь Frontend App, SOAP Auth Service, JSON User Service гэсэн үндсэн 3 бүрэлдэхүүнээс тогтоно. Хэрэглэгч бүртгүүлэх болон нэвтрэх үед frontend нь SOAP service-тэй холбогдож ажиллах ба profile үүсгэх, харах, шинэчлэх, устгах хүсэлтүүдийг bearer token-ий хамт JSON service рүү илгээнэ.

JSON service нь хүсэлт бүрийн өмнө SOAP service рүү `ValidateToken` хүсэлт илгээж token-ийг шалгана. Хэрэв token хүчинтэй бол profile storage-д хандаж CRUD үйлдлийг гүйцэтгэнэ, харин token буруу эсвэл байхгүй бол `401 Unauthorized` хариу буцаана.

---

## Өгөгдлийн сангийн сонголт

Өгөгдлийн сангийн зохион байгуулалтын хувьд SOAP Auth Service болон JSON User Service нь өөр өөрийн мэдээллийг тусад нь хадгалах байдлаар төлөвлөсөн.

SOAP Auth Service нь authentication-тэй холбоотой `username`, `password`, `token` мэдээллийг хадгална. Харин JSON User Service нь хэрэглэгчийн profile мэдээлэл болох `id`, `name`, `email`, `bio`, `phone` зэрэг өгөгдлийг хадгална.

Лабораторийн ажлын хэрэгжилт дээр database-ийн оронд энгийн байдлаар in-memory storage ашиглаж туршсан.

