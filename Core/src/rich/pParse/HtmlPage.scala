/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */package rich
package pParse

object HtmlPage
{
   def apply(hMems: XCon *)(bMems: XCon *): HtmlPage = new HtmlPage
   {
      override def head: HHead = HHead(hMems: _*)
      override def body: HBody = HBody(bMems: _*)
   }
   def out(hMems: XCon *)(bMems: XCon *): String = apply(hMems: _*)(bMems: _*).out
   def get(hMems: Seq[XCon], bMems: Seq[XCon]): String = new HtmlPage
   {
      def head = HHead.get(hMems)
      def body = HBody.get(bMems)
   }.out
}

trait HtmlPage
{
   def head: HHead
   def body: HBody
   def out: String = "<!DOCTYPE html>".nl - head.out(0).nl - body.out(0).nl - "</html>"
      //"<!DOCTYPE html>\n<head>" - hMems.multiFold(0) -+ "</head>\n<body>" - bMems.multiFold(0) -+ "</body>\n</html>"  
}