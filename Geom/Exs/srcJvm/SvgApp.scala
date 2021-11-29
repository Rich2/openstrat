/* Copyright 2018-21 Licensed under Apache Licence version 2.0. */
package ostrat; package geom; package pExs
import Colour._

/** An example app using the Graphics module to produce SVG. */
object SvgApp extends App 
{ val cof1 = Circle(80).fill(Orange).svgInline  
  val cof2 = Circle(120).fill(Red).svgInline
  val cof3 = Circle(80, 80, 50).fill(Gold).svgInline
  val cof4 = Arr(Circle(100, -50, 50).fill(Pink), Circle(100, 50, -50).fill(Colour.LightBlue)).svgInline()
  val e1 = Ellipse(90, 60)
  val ef1 = e1.fill(DarkMagenta).svgInline
  val ef2 = e1.rotate15.fill(Colour.BurlyWood).svgInline
  val s3 = "<svg><circle cx=75 cy=75 r=75 fill=orange /><circle cx=225 cy=75 r=75 fill=violet /><circle cx=300 cy=75 r=25 fill=red /></svg>"  
  val s3Sub = s3.htmlReservedSubstitute
  val ef3 = Arr(Circle(75, 75, 0).fill(Orange), Circle(75, 225, 0).fill(Violet), Circle(25, 300, 0).fill(Red)).svgInline()
  
  val bodyStr: String = 
    s"""<p>So this is some relatively simple HTML. The formatting of this example has deliberately been kept simple. For that reason no CSS has been
    |  used.
    |</p>
    
    |<p>80 diameter orange Circle below.<br> 
    |<code>Circle(80).fill(Orange).svgInline</code>
    |</p>
    |
    |$cof1
    |
    |<p>120 diameter red Circle below. OpenStrat's default geometry is relative to the centre, relative to x = 0, y = 0. The svgInline method creates
    |  a viewport with viewBox to fit the bounding rectangle of the ShapaGraphic. It also converts the graphics from openstrat coordinates to SVG's
    |  top left y is down coordinate system.<br>
    |  <code>Circle(120).fill(Red).svgInline</code>
    |</p>
    |
    |$cof2
    |
    |<p>Another 80 diameter circle, but this one is centred on x = 80, y = 50. The SVG viewPort still views the bounding rectangle of the Circle.<br>
    |  <code>Circle(120, 80, 50).fill(Gold).svgInline</code>
    |</p>
    |
    |$cof3
    |
    |<p>Two circles displayed within and relative to the bounding rectangle of the two shapes combined.<br>
    |  <code>Arr(Circle(100, -50, 50).fill(Pink), Circle(100, 50, -50).fill(Colour.LightCoral)).svgInline</code>
    |</p>
    |
    |$cof4
    |
    |<p>An ellipse 180 wide by 60 high.<br>
    |  <code>val e1 = Ellipse(90, 60)<br>
    |  val ef1 = e1.fill(DarkMagenta).svgInline</code>
    |</p>
    |
    |$ef1
    |
    |<p>The above ellipse rotated.<br>
    |  <code>val ef2 = e1.rotate15.fill(Colour.BurlyWood).svgInline</code>
    |</p>
    |
    |$ef2
    |
    |<p>The SVG below has been written directly in SVG. The rest of the SVG has been created with openstrat Scala code. It demonstrates the 300 by
    |  150 default viewbox dimensions.</br>
    | <code>$s3Sub</code></p>
    |$s3
    |
    |<p>And here's the above shapes from Scala code.<br>
    |  <code>Arr(Circle(75, 75, 0).fill(Orange), Circle(75, 225, 0).fill(Violet), Circle(25, 300, 0).fill(Red)).svgInline()</code>
    |</p>
    |
    |$ef3""".stripMargin
  val r = opensettHtmlWrite("SvgPage1", bodyStr)
  deb(r.toString)
}