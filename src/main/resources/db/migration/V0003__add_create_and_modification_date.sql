ALTER TABLE patient ADD COLUMN createDate timestamp;
ALTER TABLE patient ADD COLUMN lastModificationDate timestamp;
ALTER TABLE examination ADD COLUMN createDate timestamp;
ALTER TABLE examination ADD COLUMN lastModificationDate timestamp;