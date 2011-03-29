ALTER TABLE `app_user` ADD COLUMN `email` VARCHAR(50) NOT NULL, 
                       ADD COLUMN `phone_number` VARCHAR(100),
                       ADD COLUMN `date_of_birth` DATETIME NOT NULL;
ALTER TABLE `app_user` CHANGE `hostel_room` `hostel_room` VARCHAR(10);
