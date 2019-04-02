/*========================================================================================
procedure name	: usp_get_gachaLog
Worker	:
date	: 2017-06-26
Working server	: logdb
========================================================================================*/
/* 보물함 로그  조회 */
DROP procedure IF EXISTS `usp_get_gachaLog`;

DELIMITER //
Create Procedure usp_get_gachaLog (
    IN __usn BIGINT,
    IN __startDate dateTime,
    IN __endDate dateTime
)
DETERMINISTIC
BEGIN
    -- Select Statement Here
   select a.product_code, a.currency_type, a.currency_count, a.class_type, a.platform, b.item_type, b.iid, a.reg_date
    from product_buy_logs a
    inner join item_create_non_stackable_logs b on a.action_id = b.action_id and a.usn = b.usn
    inner join action_logs c on b.action_id = c.action_id and b.usn = c.usn
    where c.action_type = 33
    and a.usn = __usn
    and a.reg_date >= __startDate and a.reg_date <= __endDate;
END //
DELIMITER ;