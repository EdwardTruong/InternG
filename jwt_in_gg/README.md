
# ** VN **


Đây là dự án được biên soạn lại với project cũ (Docter_care) nhưng được tách phần (jwt-security) ra thành 1 phần riêng biệt. 

    Bạn có thể sử dụng và mở rộng bằng cách thay đổi tên bảng, hoặc sữa giá trị của bảng thay vì many-to-many với Role thì thay đổi thành RolePermission và UserPermission.

    Nếu đã sử dụng RolePermission & UserPermission hãy thay đổi UserDetails của Spring security và hàm loadByUsername của UserDetailsService. 
 
    Trong class cấu hình SpringSecurity có 2 đối tượng được mở rộng và cùng có 1 chức năng cho filter (AuthTokenFilter - JWTFilter) hãy xóa 1 và dùng 1 trong 2. 

    Hãy tìm hiểu về (GenericFilterBean) và (OncePerRequestFilter). Để biết cách sử dụng. TokenProvider và TokenProviderDuc là như nhau. Không sao cả.

# ** EN **

This is a project that has been recompiled from the old project (Doctor_Care) but with the (jwt-security) section separated into an independent module.

    You can use and extend by changing the table names or modifying the table values. Instead of many-to-many with Role, you can change it to RolePermission and UserPermission.
    
    If you are already using RolePermission and UserPermission, make sure to update the Spring Security UserDetails and the loadByUsername method in UserDetailsService.

    In the Spring Security configuration class, there are two extended objects with the same functionality for filtering (AuthTokenFilter - JWTFilter). Remove one and use the other.

    Learn about GenericFilterBean and OncePerRequestFilter to understand how to use them effectively. TokenProvider and TokenProviderDuc are essentially the same, so there’s no issue
## Knowledges

 - Spring Boot Restful API
 - Spring Security
 - Jwt



## Functions

Login : 
When a user logs in, a JWT is generated. And when the user uses any feature, the JWT will be checked to authorize or deny that request

    
    //LoginRequest
    {
    "username":"user_01@gmail.com",
    "password":"123"
    }
    // Bearer Token : eyJhbGciOiJIUzI1NiJ9.
    eyJzdWIiOiJ0cnVvbmdlZDQ2QGdtYWlsLmNvbSIsImlhdCI6MTcyNDc2Mzc2NywiZXhwIjoxNzI0NzY0NjY3fQ.
    2NxtiCQSrOd8Za5yeGrtHOLUADqoP_5f9b3zA-lmGcw  



    


    
    








 
    

## Authors



- [@TruongTNFx](https://www.github.com/TruongTNFx)











    
