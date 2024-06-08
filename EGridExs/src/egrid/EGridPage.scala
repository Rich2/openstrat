/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package egrid
import pWeb._

/** Documentation page for EGrid Module." */
object EGridPage extends HtmlPage
{
  override def head: HtmlHead = HtmlHead.titleCss("EGrid Module", "https://richstrat.com/Documentation/documentation")

  override def body: HtmlBody = HtmlBody(HtmlH1("EGrid Module"), central)

  def central: HtmlDiv = HtmlDiv.classAtt("central", egrids, open)
  val egrDir: String = "/egrids/"
  def egrids: HtmlOlWithLH = HtmlOlWithLH(HtmlH2("World Hex Grids."),
    HtmlLi.linkAndText(egrDir + "eg1300app.html", "EGrid 1300km", "1300km hex scale world."),
    HtmlLi.linkAndText(egrDir + "eg1000app.html", "EGrid 1000km", "1000km hex scale world."),
    HtmlLi.linkAndText(egrDir + "eg640app.html", "EGrid 640km", "640km hex scale world."),
    HtmlLi.linkAndText(egrDir + "eg460app.html", "EGrid 460km", "460km hex scale world."),
    HtmlLi.linkAndText(egrDir + "eg320app.html", "EGrid 320km", "320km hex scale world."),
    HtmlLi.linkAndText(egrDir + "eg220europe.html", "EGrid Europe 220km", "220km hex scale Europe."),
    HtmlLi.linkAndText(egrDir + "eg220europewide.html", "EGrid Europe wide 220km", "220km hex scale Europe wide."),
    HtmlLi.linkAndText(egrDir + "eg220namerica.html", "EGrid North America 220km", "220km hex scale North America."),
    HtmlLi.linkAndText(egrDir + "eg160europe.html", "EGrid Europe 160km", "160km hex scale Europe."),
    HtmlLi.linkAndText(egrDir + "eg120europe.html", "EGrid Europe 120km", "120km hex scale Europe."),
    HtmlLi.linkAndText(egrDir + "eg80europe.html", "EGrid Europe 80km", "80km hex scale Europe."),
    HtmlLi.linkAndText(egrDir + "earthapp.html", "Earth areas", "Earth irregular areas."),
  )


  def open = HtmlP("Hex tile grids for the Earth at various scales, 320km, 220km, 160km, 120km and 80km. The tile grids work the same as normal" --
    "hex grids from in the\n  Tiling module, except they have to be joined togethor at the 15, 45, 75 degree longitude boundaries." ---
    "<br>A = 3 * √3 * R² / 2 //Where A is area and R is both the long radius and side length of the regualar hexagon." ---
    "<br>R = 2 * r / √3 = d / √3 //Where r is the short radius and d is the short diameter or hex scale." ---
    "<br>R² = d² / 3" ---
    "<br>A = √3 * d² / 2" ---
    "<br>A = 0.8660254037844386 * d²" ---
    "<br>MIA = A/6 //where MIA is minimum island area." ---
    "<br>MIA = 0.14433756729740643 * d²")
}