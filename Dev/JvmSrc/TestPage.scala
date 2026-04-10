/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb.*

object TestPage extends PageHtml
{
  override def head: HeadHtml = HeadHtml.title("Test Page")

  override def body: BodyHtml = BodyHtml("This is a test page", SubmitInput("send1", "Send"))
}