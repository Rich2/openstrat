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
    writeFile(path)
  }{
    strArr => deb(strArr.mkStr(","))
  }

  def writeFile(path: DirPathAbs): Unit =
  { val noCacheScript = s"""
    |  // aid local development in ensuring script not cached during a simple refresh
    |  var script = document.createElement("script");
    |  script.setAttribute("type", "text/javascript");
    |  script.setAttribute("src", "../../GenAppJs/target/scala-$scalaVersionStr/genappjs-fastopt.js?"+Date.now().toString());
    |  document.getElementsByTagName("head")[0].appendChild(script);
    |  script.addEventListener('load', function(e) { EG460AppJs.main(); });
    |""".stripMargin

    val style = HtmlStyle(CssBody(DecMarg(0.px), DecOverflowHidden))
    val head = HtmlHead.title("OpenStrat: 460km All Longitudes full", HtmlNoCache, style)
    val script = HtmlScript.inlineJsStr(noCacheScript)
    val body = HtmlBody(HtmlCanvas.id("scanv"), HtmlNoScript(), script)
    val page = HtmlPage(head, body)
    val p2: DirPathAbs = path
    val res = fileWrite(path / "Dev" / "target", "EG460SbtFast.html", page.out)
    deb(res.toString)
  }
}
