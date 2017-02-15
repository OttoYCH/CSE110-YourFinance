1. ./model contains the implementation of database. You can skip them if you are not interested in the implementation.
2. ./data contains the classes such as Group, PersonalExpense, and etc. These objects are what you'll
   need to store and retrieve data.

   !CAUTION: DO NOT use setter method on ID and createdDate cause they will be generated automatically
    when you create and upload a new object to DB.

3. ./manager contain interfaces with methods you will need to request(upload and download) data
   from AWS DynamoDB. DynamoDB will translate the data object to tables or vice tra with these methods.

    Example 1: find all the groups a user has.

    Runnable runnable = new Runnable() {
      public void run() {

        GroupManager gm = ManagerFactory.getGroupManager(getApplicationContext());
        List<Group> groupList = gm.getAllGroupsOfUser(/USERID/);

      }
    };
    Thread mythread = new Thread(runnable);
    mythread.start();


    Example 2: create a new personal expense to a user.

    Runnable runnable = new Runnable() {
      public void run() {

        PersonalExpense pe = new PersonalExpense(/USERID/, 10.5, "Burger king", "Food");
        PersonalExpenseManager peManager = ManagerFactory.getPersonalExpenseManager(getApplicationContext());
        peManager.createExpense(pe);

      }
    };
    Thread mythread = new Thread(runnable);
    mythread.start();