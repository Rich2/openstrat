/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb.*, utiljvm.*

object TestPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.title("Test Page")

  override def body: HtmlBody = HtmlBody("This is a test page".xCon, HtmlInput.submit("Send"))
}
