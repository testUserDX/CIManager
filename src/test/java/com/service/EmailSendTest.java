package com.service;

import com.DomainTestBase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class EmailSendTest extends DomainTestBase{

    @Autowired
    private EmailService emailService;

    @Test
    public void successfulEmailSending() {
        boolean result = emailService.sendEmail("test@gmail.com", "TestSubject", "TestText");
        assertTrue(result);
    }

    @Test
    public void failedEmailSending() {
        boolean result = emailService.sendEmail("notEmail", "TestSubject", "TestText");
        assertFalse(result);
    }
}
