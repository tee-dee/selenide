package com.codeborne.selenide;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConditionTest {
  @Test
  public void displaysHumanReadableName() {
    assertEquals("visible", Condition.visible.toString());
    assertEquals("hidden", Condition.hidden.toString());
    assertEquals("attribute lastName=Malkovich", Condition.hasAttribute("lastName", "Malkovich").toString());
  }

  @Test
  public void textConditionIsCaseInsensitive() {
    WebElement element = mock(WebElement.class);
    when(element.getText()).thenReturn("John Malkovich The First");
    assertTrue(Condition.text("john malkovich").apply(element));
  }

  @Test
  public void textConditionIgnoresWhitespaces() {
    WebElement element = mock(WebElement.class);
    when(element.getText()).thenReturn("John  the\n Malkovich");
    assertTrue(Condition.text("john the malkovich").apply(element));
  }

  @Test
  public void textCaseSensitive() {
    WebElement element = mock(WebElement.class);
    when(element.getText()).thenReturn("John Malkovich The First");
    assertFalse(Condition.textCaseSensitive("john malkovich").apply(element));
    assertTrue(Condition.textCaseSensitive("John Malkovich").apply(element));
  }

  @Test
  public void textCaseSensitiveIgnoresWhitespaces() {
    WebElement element = mock(WebElement.class);
    when(element.getText()).thenReturn("John Malkovich\t The   \n First");
    assertFalse(Condition.textCaseSensitive("john malkovich").apply(element));
    assertTrue(Condition.textCaseSensitive("John        Malkovich The   ").apply(element));
  }

  @Test
  public void reduceSpacesRemovesReplacesMultipleSpacesBySingleSpace() {
    Condition condition = Condition.text("abc");
    assertEquals("Bruce Willis", condition.reduceSpaces("Bruce   \n\t   Willis"));
    assertEquals("", condition.reduceSpaces(""));
    assertEquals("", condition.reduceSpaces("   "));
    assertEquals("a", condition.reduceSpaces("a"));
    assertEquals("a", condition.reduceSpaces("  a\n"));
    assertEquals("Bruce Willis", condition.reduceSpaces("     Bruce   \n\t   Willis  \n\n\n"));
  }

  @Test
  public void exactTextIsCaseInsensitive() {
    WebElement element = mock(WebElement.class);
    when(element.getText()).thenReturn("John Malkovich");
    assertTrue(Condition.exactText("john malkovich").apply(element));
    assertFalse(Condition.exactText("john").apply(element));
  }

  @Test
  public void exactTextCaseSensitive() {
    WebElement element = mock(WebElement.class);
    when(element.getText()).thenReturn("John Malkovich");
    assertFalse(Condition.exactTextCaseSensitive("john malkovich").apply(element));
    assertTrue(Condition.exactTextCaseSensitive("John Malkovich").apply(element));
    assertFalse(Condition.exactText("John").apply(element));
  }
}
