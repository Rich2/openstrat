/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pWeb.*

object DevHtmls extends App
{
  deb("Starting DevHtmls")
  val sett = findDevSettingT[DirPathAbs]("projPath")

  sett.forGoodForBad { path =>
    deb(path.str)

   val bodyContent = """
        |  <script type='text/javascript'>
        |  // aid local development in ensuring script not cached during a simple refresh
        |    var script = document.createElement("script");
        |    script.setAttribute("type", "text/javascript");
        |    script.setAttribute("src", "../../GenAppJs/target/scala-3.4.2/genappjs-fastopt.js?"+Date.now().toString());
        |    document.getElementsByTagName("head")[0].appendChild(script);
        |    script.addEventListener('load', function(e) {
        |    EG460AppJs.main();
        |  });
        |  </script>
        |""".stripMargin

    val style = HtmlStyle(CssBody(DecMarg(0.px), DecOverflowHidden))
    val head = HtmlHead.title("OpenStrat: 460km All Longitudes full", HtmlNoCache, style)
    val body = HtmlBody(HtmlCanvas.id("scanv"), HtmlNoScript(), bodyContent.xCon)
    val page = HtmlPage(head, body)
    val p2: DirPathAbs = path
    val res = fileWrite(path / "Dev" / "target", "EG460SbtFast.html", page.out)
    deb(res.toString)
  }{
    strArr => deb(strArr.mkStr(","))
  }
}
