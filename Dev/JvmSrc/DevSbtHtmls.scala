/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pWeb.*

trait DevHtmls
{ def scalaVersionStr = "3.5.2"
  def appNames = StrArr("Diceless", "Discov", "IndRev", "Sors", "WW1", "WW2", "BC305", "Zug", "Dungeon", "Planets", "Chess")
  def egridNames = StrArr("EG1300", "EG1000", "EG640", "EG460", "EG320")
  def toolStr: String
  def makeFile(path: DirPathAbs, outerModuleName: String, isFast: Boolean, name: String): HtmlPage

  def writeFastFull(path: DirPathAbs, outerModuleName: String, name: String): Unit =
  { val fastPage = makeFile(path, outerModuleName, true, name)
    fileWrite(path / "Dev" / "target" / "DevPages", s"$name${toolStr}Fast.html", fastPage.out)
    val fullPage = makeFile(path, outerModuleName, false, name)
    fileWrite(path / "Dev" / "target" / "DevPages", s"$name${toolStr}Full.html", fullPage.out)
  }
}

object DevSbtHtmls extends DevHtmls
{
  override def toolStr: String = "Sbt"

  def main(args: Array[String]): Unit =
  {
    projPathDo { path0 => args.length match
    { case 0 => deb("No args, no files created.")
      case _ if args(0).toString == "all" => appNames.foreach{name => writeFastFull(path0, "AppsJs", name) }
      case _ => args.filter( arg => appNames.exists(_ == arg)).foreach(arg => writeFastFull(path0, "EGridJs", arg)) } }
  }

  override def makeFile(path: DirPathAbs, outerModuleName: String, isFast: Boolean, name: String): HtmlPage =
  { val jsStr = ife(isFast, "fast", "")

    val noCacheScript = s"""
    |  // aid local development in ensuring script not cached during a simple refresh
    |  var script = document.createElement("script");
    |  script.setAttribute("type", "text/javascript");
    |  script.setAttribute("src", "../../../Apps/AppsJs/target/scala-$scalaVersionStr/appsjs-${name}-${jsStr}opt/main.js?"+Date.now().toString());
    |  document.getElementsByTagName("head")[0].appendChild(script);
    |  script.addEventListener('load', function(e) { ${name}AppJs.main(); });
    |""".stripMargin

    val style = HtmlStyle(CssBody(DecMarg(0.px), DecOverflowHidden))
    val head = HtmlHead.title("OpenStrat:" -- name, HtmlNoCache, style)
    val script = HtmlScript.inlineJsStr(noCacheScript)
    val body = HtmlBody(HtmlCanvas.id("scanv"), HtmlNoScript(), script)
    HtmlPage(head, body)
  }
}