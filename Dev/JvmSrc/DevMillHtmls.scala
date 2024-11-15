/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pWeb.*

object DevMillHtmls extends DevHtmls
{
  def main(args: Array[String]): Unit =
  {
    projPathDo { path => args.length match
    { case 0 => deb("No args, no files created.")
      case _ if args(0).toString == "all" =>{
        appNames.foreach{name => writeFastFull(path, "AppsJs", name) }
        egridNames.foreach{name => writeFastFull(path, "EGridJs", name) }
      }
      case _ => args.filter( arg => appNames.exists(_ == arg)).foreach(arg => writeFastFull(path, "AppsJs", arg)) } }
  }

  def writeFastFull(path: DirPathAbs, outerModuleName: String, name: String): Unit =
  { writeFile(path, outerModuleName, true, name)
    writeFile(path, outerModuleName, false, name)
  }

  def writeFile(path: DirPathAbs, outerModuleName: String, isFast: Boolean, name: String): Unit =
  { val jsStr = ife(isFast, "fast", "")
    val htmlStr = ife(isFast, "Fast", "Full")

    val noCacheScript = s"""
    |  // aid local development in ensuring script not cached during a simple refresh
    |  var script = document.createElement("script");
    |  script.setAttribute("type", "text/javascript");
    |  script.setAttribute("src", "../../../Out/$outerModuleName/$name/scala-$scalaVersionStr/appsjs-${name}-${jsStr}opt/main.js?"+Date.now().toString());
    |  document.getElementsByTagName("head")[0].appendChild(script);
    |  script.addEventListener('load', function(e) { ${name}AppJs.main(); });
    |""".stripMargin

    val style = HtmlStyle(CssBody(DecMarg(0.px), DecOverflowHidden))
    val head = HtmlHead.title("OpenStrat:" -- name, HtmlNoCache, style)
    val script = HtmlScript.inlineJsStr(noCacheScript)
    val body = HtmlBody(HtmlCanvas.id("scanv"), HtmlNoScript(), script)
    val page = HtmlPage(head, body)
    val res = fileWrite(path / "Dev" / "target" / "DevPages", s"${name}Mill${htmlStr}.html", page.out)
    deb(res.toString)
  }
}