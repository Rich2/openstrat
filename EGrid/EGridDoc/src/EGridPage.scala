/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, HtmlStrExts.*

/** Versionless. Creates POM files and copies Mill, JAR, artifacts for EGrid JVM module. */
object EGridPommer extends OsModuleJvmVerless(DirsRel("EGrid"), "egrid", RArr(TilingPommer, GeomPommer, UtilPommer), RArr())

/** Documentation page for EGrid Module." */
object EGridPage extends OSDocumentationPage
{ override def titleStr: String = "EGrid Module"
  override val fileNameStem: String = "egrid"
  override def body: BodyHtml = BodyHtml(titleStr.h1, central)

  def central: DivHtml = DivHtml.classAtt("central", egrids, open)
  val egrDir: String = "../egrids"
  def egrids: OlSection = OlSection("World Hex Grids.".h2,
    LiHtml(AHtml(egrDir / "eg1300.html", "EGrid 1300km"), "1300km hex scale world."),
    LiHtml(AHtml(egrDir / "eg1000.html", "EGrid 1000km"), "1000km hex scale world."),
    LiHtml(AHtml(egrDir / "eg640.html", "EGrid 640km"), "640km hex scale world."),
    LiHtml(AHtml(egrDir / "eg460.html", "EGrid 460km"), "460km hex scale world."),
    LiHtml(AHtml(egrDir / "eg320.html", "EGrid 320km"), "320km hex scale world."),
    LiHtml(AHtml(egrDir / "eg220europe.html", "EGrid Europe 220km"), "220km hex scale Europe."),
    LiHtml(AHtml(egrDir / "eg220europewide.html", "EGrid Europe wide 220km"), "220km hex scale Europe wide."),
    LiHtml(AHtml(egrDir / "eg220namerica.html", "EGrid North America 220km"), "220km hex scale North America."),
    LiHtml(AHtml(egrDir / "eg160europe.html", "EGrid Europe 160km"), "160km hex scale Europe."),
    LiHtml(AHtml(egrDir / "eg120europe.html", "EGrid Europe 120km"), "120km hex scale Europe."),
    LiHtml(AHtml(egrDir / "eg80europe.html", "EGrid Europe 80km"), "80km hex scale Europe."),
    LiHtml(AHtml(egrDir / "earthapp.html", "Earth areas"), "Earth irregular areas."),
  )

  def open: PHtml = PHtml("""Hex tile grids for the Earth at various scales, 320km, 220km, 160km, 120km and 80km. The tile grids work the same as normal hex
  |grids from in the Tiling module, except they have to be joined together at the 15, 45, 75 degree longitude boundaries.""".stripMargin,
  SpanLine("A = 3 * √3 * R² / 2 //Where A is area and R is both the long radius and side length of the regular hexagon."),
  SpanLine("R = 2 * r / √3 = d / √3 //Where r is the short radius and d is the short diameter or hex scale."),
  SpanLine("R² = d² / 3"),
  SpanLine("A = √3 * d² / 2"),
  SpanLine("A = 0.8660254037844386 * d²"),
  SpanLine("MIA = A/6 //where MIA is minimum island area."),
  SpanLine("MIA = 0.14433756729740643 * d²")
  )
}