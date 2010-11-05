INSERT INTO `app_user` (`id`,`account_expired`,`account_locked`,`address`,`city`,`country`,`postal_code`,`province`,`credentials_expired`,`email`,`account_enabled`,`first_name`,`last_name`,`password`,`password_hint`,`phone_number`,`username`,`version`,`website`) VALUES 
 (2,0x00,0x00,'Y.Kolasa 28','Minsk','BY','777777','Minsk',0x00,'to_echO@tut.by',0x01,'Andrei','Mukhin','8ca2fcb549a98cd2706904c7d8af77d8c23dd10f','(:','','cheshire',0,''),
 (3,0x00,0x00,'Y.Kolasa 28','Minsk','BY','777777','Minsk',0x00,'to_adminko@tut.by',0x01,'adminko','adminko','d033e22ae348aeb5660fc2140aec35850c4da997','=)','','adminko',0,'');

INSERT INTO `user_role` (`user_id`,`role_id`) VALUES 
 (2,-1),
 (3,-1);

