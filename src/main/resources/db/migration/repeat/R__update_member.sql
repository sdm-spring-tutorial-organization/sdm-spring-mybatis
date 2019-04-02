
-- UPDATE
UPDATE member SET addr = "change2" WHERE code = 2;
UPDATE member SET addr = "change3" WHERE code = 3;

--
/*========================================================================================
procedure name	: usp_get_journeyOfArcher
Worker	:
date	: 2018-11-16
Working server	: logdb
========================================================================================*/
/* 궁수의 여정 달성 로그 조회 */
-- DROP procedure IF EXISTS usp_get_journeyOfArcher;

DELIMITER //
Create Procedure usp_get_journeyOfArcher (
    IN __usn BIGINT,
    IN __startDate dateTime,
    IN __endDate dateTime
)
DETERMINISTIC
BEGIN

  -- Select Statement Here
	select a.id, a.reg_date, a.achiv_level, a.action_id, a.achiv_type, b.action_type, b.arg0 as action_arg0, b.arg1 as action_arg1
	from achiv_reward_logs a
	inner join action_logs b on a.action_id = b.action_id and a.usn = b.usn
	where a.usn = __usn and a.reg_date >= __startDate and a.reg_date <= __endDate
	and b.action_type = 81 and b.arg0 = 51;

END //
DELIMITER ;