/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pWeb.*

object DevHtmls extends App
{
  deb("Starting DevHtmls")
  val sett = findDevSettingT[DirPathAbs]("projPath")

  sett.forGoodForBad { path =>
    deb(path.str)
    val hStr =
      """<!DOCTYPE html>
        |<head>
        |  <title>OpenStrat: 460km All Longitudes full</title>
        |  <meta charset='UTF-8'>
        |  <meta name='viewport' content='width=device-width'>
        |  <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate"/>
        |  <meta http-equiv="Pragma" content="no-cache"/>
        |  <meta http-equiv="Expires" content="0"/>
        |  <style>
        |    body { margin: 0px; overflow: hidden; }
        |  </style>
        |</head>
        |<body>
        |  <canvas id='scanv'></canvas>
        |  <noscript>
        |    This page will not function properly without Javascript enabled
        |  </noscript>
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
        |</body>
        |
        |</html>
        |""".stripMargin

//    val head = HtmlHead()
    //val page = HtmlPage()
    val p2: DirPathAbs = path
    val res = fileWrite(path / "Dev" / "target", "EG460SbtFast.html", hStr)
    deb(res.toString)
  }{
    strArr => deb(strArr.mkStr(","))
  }
}
