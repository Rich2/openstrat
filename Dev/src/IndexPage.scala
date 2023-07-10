/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pWeb._

object IndexPage
{
  val head = HtmlHead.titleCss("Openstrat", "only")
  def body = HtmlBody.elems(XConStr(bodyStr))
  def content = HtmlPage(head, body)

  val bodyStr: String = "Hi"

//   def title: String = "Home"
//   def menuLabel = "Home"
//   def partLink: String = "index"
//   val intro: HtmlFile = HtmlFile(resDir / "IndexIntro.txt")
//   override def middle: Seq[XCon] = List(HtmlDiv(intro)(),   )
}