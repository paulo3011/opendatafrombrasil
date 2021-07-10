
# #__Establishments__

| Field                          | Description                                                                                            | Data Type     |
| ------------------------------ | ------------------------------------------------------------------------------------------------------ | ------------- |
| BASIC CNPJ                     | BASE OF THE CNPJ REGISTRATION NUMBER (FIRST EIGHT DIGITS OF CNPJ).                                     | VARCHAR(8)    |
| CNPJ ORDER                     | NUMBER OF THE ESTABLISHMENT (FROM THE NINTH TO THE TWELFTH DIGIT OF THE CNPJ).                         | VARCHAR(4)    |
| CNPJ DV                        | CNPJ CHECKING DIGIT (LAST TWO DIGITS OF THE CNPJ).                                                     | VARCHAR(2)    |
| HEADQUARTERS/BRANCH IDENTIFIER | HEADQUARTERS/BRANCH IDENTIFIER CODE: 1 - HEADQUARTERS, 2 - BRANCH                                      | SMALLINT      |
| FANTASY NAME                   | CORRESPONDS TO THE FANTASY NAME                                                                        | VARCHAR(300)  |
| REGISTRATION STATUS            | CODE OF REGISTRATION STATUS: 01 - NULL, 2 - ACTIVE, 3 - SUSPENDED, 4 - UNABLE, 08 - DOWNLOADED         | SMALLINT      |
| DATE REGISTRATION STATUS       | DATE OF REGISTRATION STATUS EVENT                                                                      | DATE          |
| REASON REGISTRATION STATUS     | CODE OF REASON FOR REGISTRATION STATUS                                                                 | INTEGER       |
| NAME OF THE CITY ABROAD        | NAME OF THE CITY ABROAD                                                                                | VARCHAR(100)  |
| COUNTRY                        | COUNTRY CODE                                                                                           | INTEGER       |
| ACTIVITY START DATE            | START DATE OF ACTIVITY                                                                                 | DATE          |
| MAIN TAX CNAE                  | CODE OF THE MAIN ECONOMIC ACTIVITY OF THE ESTABLISHMENT                                                | INTEGER       |
| SECONDARY FISCAL CNAE          | CODE OF THE SECONDARY ECONOMIC ACTIVITIES OF THE ESTABLISHMENT                                         | VARCHAR(2000) |
| ADDRESS TYPE                   | TYPE OF ADDRESS                                                                                        | VARCHAR(40)   |
| ADDRESS                        | ADDRESS WHERE THE ESTABLISHMENT IS LOCATED                                                             | VARCHAR(300)  |
| NUMBER                         | ADDRESS NUMBER WHERE THE ESTABLISHMENT IS LOCATED. WHEN THE NUMBER IS NOT FILL IN, THERE WILL BE 'Y/N' | VARCHAR(30)   |
| COMPLEMENT                     | COMPLEMENT OF THE ADDRESS WHERE THE ESTABLISHMENT IS LOCATED                                           | VARCHAR(300)  |
| DISTRICT                       | DISTRICT WHERE THE ESTABLISHMENT IS LOCATED                                                            | VARCHAR(100)  |
| ZIP CODE                       | CODE OF POSTAL ADDRESS IN WHICH THE ESTABLISHMENT IS LOCATED                                           | VARCHAR(10)   |
| STATE                          | ACRONYM OF THE UNIT OF THE FEDERATION IN WHICH THE ESTABLISHMENT IS FOUND                              | VARCHAR(2)    |
| COUNTY                         | CITY CODE OF JURISDICTION WHERE THE ESTABLISHMENT IS FOUND                                             | INTEGER       |
| Area Code 1                    | DDD 1                                                                                                  | VARCHAR(4)    |
| PHONE 1                        | PHONE NUMBER 1                                                                                         | VARCHAR(10)   |
| Area Code 2                    | DDD 2                                                                                                  | VARCHAR(4)    |
| PHONE 2                        | PHONE NUMBER 2                                                                                         | VARCHAR(10)   |
| FAX DDD                        | THE FAX DDD                                                                                            | VARCHAR(4)    |
| FAX                            | THE FAX NUMBER                                                                                         | VARCHAR(10)   |
| E-MAIL                         | THE TAXPAYER'S E-MAIL                                                                                  | VARCHAR(300)  |
| SPECIAL SITUATION              | SPECIAL COMPANY SITUATION                                                                              | VARCHAR(300)  |
| DATE OF SPECIAL SITUATION      | DATE ON WHICH THE COMPANY ENTERED IN A SPECIAL SITUATION                                               | DATE          |

</br>

# #__Companies__

| Field                            | Description                                                                                                                                                        | Data Type     |
| -------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------ | ------------- |
| BASIC CNPJ                       | BASE OF THE CNPJ REGISTRATION NUMBER (FIRST EIGHT DIGITS OF CNPJ).                                                                                                 | VARCHAR(8)    |
| CORPORATE NAME / BUSINESS NAME   | CORPORATE NAME OF THE LEGAL ENTITY                                                                                                                                 | VARCHAR(300)  |
| LEGAL NATURE                     | CODE OF LEGAL NATURE                                                                                                                                               | INTEGER       |
| QUALIFICATION OF THE RESPONSIBLE | QUALIFICATION OF THE INDIVIDUAL RESPONSIBLE FOR THE COMPANY                                                                                                        | SMALLINT      |
| COMPANY SHARE CAPITAL            | COMPANY SHARE CAPITAL                                                                                                                                              | NUMERIC(14,2) |
| COMPANY SIZE                     | COMPANY SIZE CODE: 1 - NOT INFORMED, 2 - MICRO COMPANY, 03 - SMALL COMPANY, 05 - OTHER                                                                             | SMALLINT      |
| RESPONSIBLE FEDERATIVE ENTITY    | THE RESPONSIBLE FEDERATIVE ENTITY IS COMPLETED FOR THE CASES OF ORGANS AND ENTITIES OF THE GROUP OF LEGAL NATURE 1XXX. FOR OTHER NATURES, THIS ATTRIBUTE IS BLANK. | VARCHAR(300)  |

</br>

# #__Partners__

| Field                                                                                   | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 | Data Type    |
| --------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ------------ |
| BASIC CNPJ                                                                              | BASE OF THE CNPJ REGISTRATION NUMBER (FIRST EIGHT DIGITS OF CNPJ).                                                                                                                                                                                                                                                                                                                                                                                                                                                                          | VARCHAR(8)   |
| MEMBER IDENTIFIER                                                                       | MEMBER IDENTIFIER CODE 1 - LEGAL ENTITY, 2 - INDIVIDUAL, 3 - FOREIGN                                                                                                                                                                                                                                                                                                                                                                                                                                                                        | SMALLINT     |
| NAME OF THE PARTNER (IN THE PHYSICAL PERSON CASE) OR CORPORATE NAME (IN THE LEGAL CASE) | NAME OF THE PARTNER INDIVIDUAL OR THE CORPORATE NAME AND/OR NAME OF THE LEGAL ENTITY AND/OR NAME OF THE PARTNER/CORPORATE NAME OF THE FOREIGN PARTNER                                                                                                                                                                                                                                                                                                                                                                                       | VARCHAR(300) |
| CNPJ/CPF OF THE PARTNER                                                                 | CPF OR CNPJ OF THE PARTNER (FOREIGN PARTNER DOES NOT HAVE THIS INFORMATION).                                                                                                                                                                                                                                                                                                                                                                                                                                                                | VARCHAR(20)  |
| MEMBER QUALIFICATION                                                                    | MEMBER QUALIFICATION CODE                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   | INTEGER      |
| DATE OF ENTRY INTO THE COMPANY                                                          | WHEN THE SOCIETY BEGAN                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      | DATE         |
| COUNTRY                                                                                 | COUNTRY CODE OF FOREIGN PARTNER                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             | INTEGER      |
| LEGAL REPRESENTATIVE                                                                    | CPF NUMBER OF THE LEGAL REPRESENTATIVE                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      | VARCHAR(300) |
| REPRESENTATIVE'S NAME                                                                   | NAME OF LEGAL REPRESENTATIVE                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                | VARCHAR(300) |
| LEGAL REPRESENTATIVE QUALIFICATION                                                      | LEGAL REPRESENTATIVE QUALIFICATION CODE                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     | INTEGER      |
| AGE RANGE                                                                               | CODE CORRESPONDING TO THE AGE RANGE OF THE MEMBER:<br><br>\- 1 for the intervals between 0 and 12 years;<br>\- 2 for the intervals between 13 and 20 years;<br>\- 3 for the intervals between 21 and 30 years;<br>\- 4 for the intervals between 31 and 40 years;<br>\- 5 for the intervals between 41 and 50 years;<br>\- 6 for the intervals between 51 and 60 years;<br>\- 7 for the intervals between 61 and 70 years;<br>\- 8 for the intervals between 71 and 80 years; - 9 for people over 80 years old.<br>\- 0 for not applicable. | SMALLINT     |

</br>

# #__National Classification of Economic Activities__

| Field | Description               | DATA TYPE |
| ----- | ------------------------- | --------- |
| CODE  | CODE OF ECONOMIC ACTIVITY | INTEGER   |
| NAME  | DESCRIPTION               | STRING    |

</br>

# #__Legal Nature__

| Field | Description       | DATA TYPE |
| ----- | ----------------- | --------- |
| CODE  | LEGAL NATURE CODE | INTEGER   |
| NAME  | DESCRIPTION       | STRING    |

</br>

# #__Partner Qualification__

| Field | Description                   | Data Type |
| ----- | ----------------------------- | --------- |
| CODE  | MEMBERSHIP QUALIFICATION CODE | INTEGER   |
| NAME  | DESCRIPTION                   | STRING    |

</br>

# #__City Code__

| Field | Description | Data Type |
| ----- | ----------- | --------- |
| CODE  | CITY CODE   | INTEGER   |
| NAME  | CITY NAME   | STRING    |

</br>

# #__Country Code__

| Field       | Description         | Data Type |
| ----------- | ------------------- | --------- |
| CODE        | COUNTRY CODE        | INTEGER   |
| DESCRIPTION | NAME OF THE COUNTRY | STRING    |

</br>

# #__Simple National__

| Field                          | Description                                                                          | Data Type  |
| ------------------------------ | ------------------------------------------------------------------------------------ | ---------- |
| BASIC CNPJ                     | BASE OF THE CNPJ REGISTRATION NUMBER (FIRST EIGHT DIGITS OF CNPJ).                   | VARCHAR(8) |
| OPTION FOR SIMPLE              | INDICATOR OF THE EXISTENCE OF THE OPTION FOR SIMPLE. Y - YES, N - NO, BLANK - OTHERS | BOOLEAN    |
| DATE OF OPTION FOR SIMPLE      | DATE OF OPTION FOR SIMPLE                                                            | DATE       |
| DATE OF EXCLUSION FROM SIMPLE  | DATE OF EXCLUSION FROM SIMPLE                                                        | DATE       |
| OPTION BY MEI                  | INDICATOR OF THE EXISTENCE OF THE OPTION BY MEI Y - YES, N - NO, BLANK - OTHERS      | BOOLEAN    |
| DATE OF OPTION BY MEI          | DATE OF OPTION BY MEI                                                                | DATE       |
| DATE OF EXCLUSION FROM THE MEI | DATE OF EXCLUSION FROM THE MEI                                                       | DATE       |

</br>