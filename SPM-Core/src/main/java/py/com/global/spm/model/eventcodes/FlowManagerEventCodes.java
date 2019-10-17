package py.com.global.spm.model.eventcodes;

public enum FlowManagerEventCodes {

    SUCCESS(0L, "Success"),
    APPROVAL_TIME_EXPIRED(1L, "Approval time expired"),
    INCORRECT_PIN(2L, "Incorrect PIN"),
    INCORRECT_PIN_LAST_CHANCE(3L, "Incorrect PIN last chance"),
    INCORRECT_PIN_USER_BLOCKED(4L, "Incorrect PIN user blocked"),
    INACTIVE_USER(5L, "Inactive user"),
    PAYMENT_ORDER_CANCELATION(6L, "Payment order canceled"),
    INVALID_PAYMENT_ORDER_STATE(7L, "Invalid payment order state"),
    PAYMENT_ORDER_GENERATED(8L, "Paymentorder generated"),
    SIGNER_NOTIFICATE(9L, "Signer notificate"),
    NOT_SIGNER_TURN(10L, "It is not your turn to approve"),
    OTHER_ERROR(11L, "Other error"),
    SIGNER_NOTIFICATE_EMAIL(12L, "Signer notificate email"),
    NO_EXIST_USER(13L, "User does not exist"),
    NO_EXIST_SIGNERS(14L,"There are no signers");

    private Long idEventCode;
    private String description;

    private FlowManagerEventCodes(Long idEventCode, String description) {
        this.idEventCode = idEventCode;
        this.description = description;
    }

    public Long getIdEventCode() {
        return idEventCode;
    }

    public String getDescription() {
        return description;
    }

    public String toString() {
        return "[idEventCode=" + idEventCode + ", description=" + description
                + "]";
    }

    public boolean sonIguales(FlowManagerEventCodes other){
        return this.idEventCode.equals(other.getIdEventCode()) ;
    }

    /**
     * FlowManagerEventCodes.SUCCESS -- NotificationEventEnum.NOTIFY_PO_SIGNATURE_SUCCESS
     * FlowManagerEventCodes.SIGNER_NOTIFICATE -- NotificationEventEnum.NOTIFY_SIGNER_SMS
     * FlowManagerEventCodes.INVALID_PAYMENT_ORDER_STATE -- NotificationEventEnum.NOTIFY_INVALID_PAYMENT_ORDER_STATE
     * FlowManagerEventCodes.INACTIVE_USER -- NotificationEventEnum.NOTIFY_SIGNER_BLOCK
     * FlowManagerEventCodes.NOT_SIGNER_TURN -- NotificationEventEnum.NOTIFY_NOT_SIGNER_TURN
     * FlowManagerEventCodes.APPROVAL_TIME_EXPIRED -- NotificationEventEnum.NOTIFY_OUTTIME_APPOVAL
     * FlowManagerEventCodes.INCORRECT_PIN_USER_BLOCKED -- NotificationEventEnum.NOTIFY_INCORRECT_PIN_USER_BLOCKED
     * FlowManagerEventCodes.INCORRECT_PIN_LAST_CHANCE -- NotificationEventEnum.NOTIFY_INCORRECT_PIN_LAST_CHANCE
     * FlowManagerEventCodes.INCORRECT_PIN -- NotificationEventEnum.NOTIFY_PIN_NUMBER_PHONE_WRONG
     * FlowManagerEventCodes.PAYMENT_ORDER_GENERATED -- NotificationEventEnum.NOTIFY_PO_CREATED
     * FlowManagerEventCodes.PAYMENT_ORDER_CANCELATION -- NotificationEventEnum.NOTIFY_PO_CANCELED
     * FlowManagerEventCodes.OTHER_ERROR -- NotificationEventEnum.NOTIFY_OTHER_ERROR
     */
    public static NotificationEventEnum parseMessage(FlowManagerEventCodes event){
        switch (event) {
            case SUCCESS:
                return NotificationEventEnum.NOTIFY_PO_SIGNATURE_SUCCESS;
            case SIGNER_NOTIFICATE:
                return NotificationEventEnum.NOTIFY_SIGNER_SMS;
            case SIGNER_NOTIFICATE_EMAIL:
                return NotificationEventEnum.NOTIFY_SIGNER_EMAIL;
            case INVALID_PAYMENT_ORDER_STATE:
                return NotificationEventEnum.NOTIFY_INVALID_PAYMENT_ORDER_STATE;
            case INACTIVE_USER:
                return NotificationEventEnum.NOTIFY_SIGNER_BLOCK;
            case NOT_SIGNER_TURN:
                return NotificationEventEnum.NOTIFY_NOT_SIGNER_TURN;
            case APPROVAL_TIME_EXPIRED:
                return NotificationEventEnum.NOTIFY_OUTTIME_APPROVAL;
            case INCORRECT_PIN_USER_BLOCKED:
                return NotificationEventEnum.NOTIFY_INCORRECT_PIN_USER_BLOCKED;
            case INCORRECT_PIN_LAST_CHANCE:
                return NotificationEventEnum.NOTIFY_INCORRECT_PIN_LAST_CHANCE;
            case INCORRECT_PIN:
                return NotificationEventEnum.NOTIFY_INCORRECT_PIN;
            case PAYMENT_ORDER_GENERATED:
                return NotificationEventEnum.NOTIFY_PO_CREATED;
            case PAYMENT_ORDER_CANCELATION:
                return NotificationEventEnum.NOTIFY_PO_CANCELED;
            default:
                return NotificationEventEnum.NOTIFY_OTHER_ERROR;
        }
    }
}
