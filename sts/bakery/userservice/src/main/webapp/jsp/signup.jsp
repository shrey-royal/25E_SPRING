<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
    <title>Signup</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css" rel="stylesheet"/>
    <style>
        body {
            background-color: #ffffff;
            color: #000000;
        }
        .card {
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.16), 0 2px 10px rgba(0, 0, 0, 0.12);
        }
        .card-content {
            padding: 24px;
        }
        .card-title {
            font-size: 24px;
            font-weight: 300;
            margin-bottom: 20px;
        }
        .input-field label {
            color: #000000;
        }
        .input-field input[type=text],
        .input-field input[type=email],
        .input-field input[type=password] {
            border-bottom: 1px solid #000000;
        }
        .input-field input[type=text]:focus + label,
        .input-field input[type=email]:focus + label,
        .input-field input[type=password]:focus + label {
            color: #000000;
        }
        .input-field input[type=text]:focus,
        .input-field input[type=email]:focus,
        .input-field input[type=password]:focus {
            border-bottom: 1px solid #000000;
            box-shadow: 0 1px 0 0 #000000;
        }
        .btn {
            background-color: #000000;
            color: #ffffff;
        }
        .btn:hover {
            background-color: #333333;
        }
        .file-field .btn {
            background-color: #000000;
            color: #ffffff;
        }
        a {
            color: #000000;
            text-decoration: underline;
        }
        a:hover {
            color: #333333;
        }
        .waves-effect.waves-light .waves-ripple {
            background-color: rgba(0, 0, 0, 0.45);
        }
    </style>
</head>
<body class="container">
    <div class="row" style="margin-top: 50px;">
        <div class="col s12 m8 l6 offset-m2 offset-l3">
            <div class="card white">
                <div class="card-content black-text">
                    <span class="card-title center-align">Signup</span>
                    <form:form action="${pageContext.request.contextPath}/auth/signup" method="post"
                               modelAttribute="user" enctype="multipart/form-data">
                        <div class="input-field">
                            <form:input path="name" class="validate"/>
                            <label>Name</label>
                        </div>
                        <div class="input-field">
                            <form:input path="email" type="email" class="validate"/>
                            <label>Email</label>
                        </div>
                        <div class="input-field">
                            <form:password path="password" class="validate"/>
                            <label>Password</label>
                        </div>
                        <div class="file-field input-field">
                            <div class="btn">
                                <span>Upload Image</span>
                                <input type="file" name="imageFile" accept="image/*"/>
                            </div>
                            <div class="file-path-wrapper">
                                <input class="file-path validate" type="text" placeholder="Profile picture"/>
                            </div>
                        </div>
                        <button class="btn waves-effect waves-light" type="submit">Signup</button>
                    </form:form>
                    <p class="center-align" style="margin-top: 20px;">Already have an account? <a href="${pageContext.request.contextPath}/auth/signin">Sign In</a></p>
                </div>
            </div>
        </div>
    </div>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            M.AutoInit();
        });
    </script>
</body>
</html>