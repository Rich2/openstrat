/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat

object WebPage1 extends App
{
  val title = "Web Page Example 1"
  
  val body =
    """This is just some text for creating a web page.
      |
      |And here's a second paragraph.
      |""".stripMargin

  import java.io._
  val h = System.getProperty("user.home");
  val pw = new PrintWriter(new File(h + "/temp1.txt"))
  pw.write(body)
  pw.close
}
