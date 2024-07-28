/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pWeb.*

object DevHtmls extends App
{
  deb("Starting DevHtmls")
  val sett = findDevSettingT[DirPathAbs]("projPath")

  val scalaVersionStr = "3.4.2"

  sett.forGoodForBad { path =>
    deb(path.str)
    writeFastFull(path, "1300")
  }{
    strArr => deb(strArr.mkStr(","))
  }

  def writeFastFull(path: DirPathAbs, scale: String): Unit = {
    writeFile(path, true, scale)
    writeFile(path, false, scale)
  }

  def writeFile(path: DirPathAbs, isFast: Boolean, scale: String): Unit =
  {
    val jsStr = ife(isFast, "fast", "")
    val htmlStr = ife(isFast, "Fast", "Full")

    val noCacheScript = s"""
    |  // aid local development in ensuring script not cached during a simple refresh
    |  var script = document.createElement("script");
    |  script.setAttribute("type", "text/javascript");
    |  script.setAttribute("src", "../../GenAppJs/target/scala-$scalaVersionStr/genappjs-${jsStr}opt.js?"+Date.now().toString());
    |  document.getElementsByTagName("head")[0].appendChild(script);
    |  script.addEventListener('load', function(e) { EG${scale}AppJs.main(); });
    |""".stripMargin

    val style = HtmlStyle(CssBody(DecMarg(0.px), DecOverflowHidden))
    val head = HtmlHead.title("OpenStrat: 460km All Longitudes full", HtmlNoCache, style)
    val script = HtmlScript.inlineJsStr(noCacheScript)
    val body = HtmlBody(HtmlCanvas.id("scanv"), HtmlNoScript(), script)
    val page = HtmlPage(head, body)
    val res = fileWrite(path / "Dev" / "target", s"EG${scale}Sbt${htmlStr}.html", page.out)
    deb(res.toString)
  }
}
