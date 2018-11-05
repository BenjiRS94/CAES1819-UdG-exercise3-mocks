package org.udg.caes.account;

import mockit.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestAccountService {

  @Injectable AccountManager am;
  @Tested AccountService as;

  @BeforeEach
  void setup() {
    as = new AccountService();
    as.setAccountManager(am);
  }

  @Test
  void testTransfer(@Injectable Account a1, @Injectable Account a2)  {

    new Expectations() {{
      am.findAccount("id1"); result = a1;
      am.findAccount("id2"); result = a2;
    }};

    as.transfer("id1", "id2", 50);

    new Verifications() {{
      a1.debit(50); times = 1;
      a2.credit(50); times = 1;
      am.updateAccount(a1); times = 1;
      am.updateAccount(a2); times = 1;
    }};
  }
}