package com.werockstar.androidfacetracker.presenter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class MainPresenterTest {

    @Mock
    MainPresenter.View view;
    MainPresenter presenter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new MainPresenterImpl(view);
    }

    @Test
    public void presenter_should_be_not_null() throws Exception {
        assertNotNull(presenter);
    }
}