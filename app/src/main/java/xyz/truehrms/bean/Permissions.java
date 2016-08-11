package xyz.truehrms.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Permissions {

    @SerializedName("StatusCode")
    @Expose
    private float statusCode;
    @SerializedName("Version")
    @Expose
    private String version;
    @SerializedName("Result")
    @Expose
    private List<Result> result = new ArrayList<Result>();
    @SerializedName("Errors")
    @Expose
    private List<Object> errors = new ArrayList<Object>();

    /**
     * @return The statusCode
     */
    public float getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode The StatusCode
     */
    public void setStatusCode(float statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return The version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @param version The Version
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * @return The result
     */
    public List<Result> getResult() {
        return result;
    }

    /**
     * @param result The Result
     */
    public void setResult(List<Result> result) {
        this.result = result;
    }

    /**
     * @return The errors
     */
    public List<Object> getErrors() {
        return errors;
    }

    /**
     * @param errors The Errors
     */
    public void setErrors(List<Object> errors) {
        this.errors = errors;
    }

    public static class Result {

        @SerializedName("Id")
        @Expose
        private int id;
        @SerializedName("employee_id")
        @Expose
        private int employeeId;
        @SerializedName("menu_id")
        @Expose
        private int menuId;
        @SerializedName("action_id")
        @Expose
        private int actionId;
        @SerializedName("isActive")
        @Expose
        private boolean isActive;
        @SerializedName("MenuName")
        @Expose
        private String menuName;
        @SerializedName("ActionName")
        @Expose
        private String actionName;
        @SerializedName("Controller")
        @Expose
        private String controller;

        /**
         * @return The id
         */
        public int getId() {
            return id;
        }

        /**
         * @param id The Id
         */
        public void setId(int id) {
            this.id = id;
        }

        /**
         * @return The employeeId
         */
        public int getEmployeeId() {
            return employeeId;
        }

        /**
         * @param employeeId The employee_id
         */
        public void setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
        }

        /**
         * @return The menuId
         */
        public int getMenuId() {
            return menuId;
        }

        /**
         * @param menuId The menu_id
         */
        public void setMenuId(int menuId) {
            this.menuId = menuId;
        }

        /**
         * @return The actionId
         */
        public int getActionId() {
            return actionId;
        }

        /**
         * @param actionId The action_id
         */
        public void setActionId(int actionId) {
            this.actionId = actionId;
        }

        /**
         * @return The isActive
         */
        public boolean getIsActive() {
            return isActive;
        }

        /**
         * @param isActive The isActive
         */
        public void setIsActive(boolean isActive) {
            this.isActive = isActive;
        }

        /**
         * @return The menuName
         */
        public String getMenuName() {
            return menuName;
        }

        /**
         * @param menuName The MenuName
         */
        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        /**
         * @return The actionName
         */
        public String getActionName() {
            return actionName;
        }

        /**
         * @param actionName The ActionName
         */
        public void setActionName(String actionName) {
            this.actionName = actionName;
        }

        /**
         * @return The controller
         */
        public String getController() {
            return controller;
        }

        /**
         * @param controller The Controller
         */
        public void setController(String controller) {
            this.controller = controller;
        }
    }
}