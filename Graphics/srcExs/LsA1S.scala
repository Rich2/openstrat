/* Copyright 2018-20 Licensed under Apache Licence version 2.0. */
package learn
import ostrat._, geom._, pCanv._, Colour._, pWeb._

object LsA1 extends GraphicsA
{ val title = "Lesson A1"

  val output = Arr(
    Circle(100, 0, 0).fill(SeaGreen),
    Circle(70, 50, 80).fill(Orange),
    Circle(80, 300, 0).fill(Red),
    Circle(80, -250, 150).fill(LemonLime),
    Circle(40, 0, -220).fill(DarkGoldenRod),
  )

  val head = HtmlHead(Arr(HtmlTitle("Lesson A1")))
  val bodyStr =
    """<h1>Lesson A1</h1>
      |<p>This lesson covers Circles and Ellipses. As with the other lessons there will be a summary for those familiar with Scala and anumber of step
      |by step parts for those new to Scala, programming or geometry and graphics. </p>""".stripMargin
  override val page: HtmlPage = HtmlPage(head, HtmlBody(bodyStr))
}

case class LsA1(canv: CanvasPlatform) extends CanvasNoPanels("Lesson A1")
{
  repaint(LsA1.output)
}