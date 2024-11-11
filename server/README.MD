## Quickstart

1. Download wildfly full profile server (I deploy on 27.0.1)
2. Add datasource for postgres DB named ``java:jboss/datasources/postgres``
3. Add file ``secret.properties`` to resources dir
4. Add ``jwt.access.key`` property (any sting, secret key for token generation)
5. Deploy ``ROOT.war`` on server
6. If you want to save DB after app undeploy set ``hibernate.hbm2ddl.auto`` property
to ``validate`` in ``resources/META-INF/persistence.xml``


- Controllers mapped on ``/api`` url
- Request/responses formats described in ``dto`` package
- All urls which start **NOT** from ``/api/auth`` need bearer jwt token in ``Authorization``
header, like: ``Authorization: Bearer <some_token>``

Test data users:
- admin:qwerty
- user:qwerty
- abuser:qwerty

Test data admin bids:
- katz:qwerty
- hot_dog:qwerty