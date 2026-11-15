
package quickchatpart3;

public class Login {

    private String registeredUsername;
    private String registeredPassword;
    private String registeredCellPhone;

    // Check username
    public boolean checkUserName(String username) {

        return username != null
                && username.length() <= 5
                && username.contains("_");
    }

    // Check password complexity
    public boolean checkPasswordComplexity(String password) {

        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecialCharacter = false;

        for (char ch : password.toCharArray()) {

            if (Character.isUpperCase(ch)) {
                hasCapital = true;
            }

            if (Character.isDigit(ch)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(ch)) {
                hasSpecialCharacter = true;
            }
        }

        return hasCapital && hasNumber && hasSpecialCharacter;
    }

    // Check South African cellphone number
    public boolean checkCellPhoneNumber(String phoneNumber) {

        if (phoneNumber == null) {
            return false;
        }

        return phoneNumber.matches("^\\+27[0-9]{9}$");
    }

    // Register user
    public String registerUser(String username,
                               String password,
                               String phoneNumber) {

        if (!checkUserName(username)) {
            return "Username is not correctly formatted.";
        }

        if (!checkPasswordComplexity(password)) {
            return "Password is not correctly formatted.";
        }

        if (!checkCellPhoneNumber(phoneNumber)) {
            return "Cell phone number is not correctly formatted.";
        }

        registeredUsername = username;
        registeredPassword = password;
        registeredCellPhone = phoneNumber;

        return "Registration successful.";
    }

    // Login user
    public boolean loginUser(String username, String password) {

        return registeredUsername != null
                && registeredUsername.equals(username)
                && registeredPassword.equals(password);
    }

    // Return login status
    public String returnLoginStatus(String username, String password) {

        if (loginUser(username, password)) {
            return "Welcome back! Login successful.";
        }

        return "Username or password incorrect. Please try again.";
    }

    // Get registered details
    public String getRegisteredUsername() {
        return registeredUsername;
    }

    public String getRegisteredCellPhone() {
        return registeredCellPhone;
    }
}
