/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb.*

object TestPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.title("Test Page")

  override def body: HtmlBody = HtmlBody("This is a test page", SubmitInput("Send"))
}