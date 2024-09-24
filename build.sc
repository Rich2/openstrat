// build.sc
import mill._, scalalib._, scalajslib._, scalanativelib._, publish._

trait Common extends ScalaModule
{ def version = "0.3.3snap"
  def scalaVersion = "3.5.1"
  def scalacOptions = Seq("-feature", "-language:higherKinds,implicitConversions", "-noindent", "-deprecation", "-encoding", "UTF-8", "-unchecked")
}

trait CommonJvm extends Common
{ Outer =>
  trait InnerTests extends ScalaTests
  { def ivyDeps = Agg(ivy"com.lihaoyi::utest:0.8.4")
    def testFramework = "utest.runner.Framework"
    def sources = T.sources(Outer.millSourcePath / "TestSrc", millSourcePath / "src")
    def resources = T.sources(Outer.millSourcePath / "TestRes", millSourcePath / "res")
  }
}

trait CommonJs extends ScalaJSModule with Common
{ def scalaJSVersion = "1.16.0"
  def ivyDeps = Agg(ivy"org.scala-js::scalajs-dom_sjs1:2.0.0")
}

object Util extends CommonJvm
{ def sources1 = T.sources(Seq("src", "srcArr", "srcParse",  "srcPersist").map(name => PathRef(millSourcePath / name)))
  def sources2 = T.sources(millSourcePath / "srcRArr", millSourcePath / "JvmSrc")
  def sources = T.sources{ sources1() ++ sources2() }
  object test extends InnerTests
}

object UtilJs extends CommonJs
{ 
  def source2 = T{
    val str = scala.io.Source.fromFile("Util/srcRArr/RArr.scala").mkString
    val str2 = str.replaceAll("AnyVal with ", "")
    os.write(T.dest / "RArr.scala", str2)
    PathRef(T.dest)
  }

  def sources = Util.sources1() :+ source2()
}

object Geom extends CommonJvm
{
  def sources1 = T.sources(Seq("src", "srcEarth", "srcGraphic", "srcGui", "srcImperial", "srcLines", "srcPoly","srcShapes", "srcTrans", "srcUnits", "srcWeb").
    map(name => PathRef(millSourcePath / name)))

  def sources2 = T.sources(millSourcePath / "JvmSrc")
  def sources = T.sources { sources1() ++ sources2() }
  def moduleDeps = Seq(Util)
  object test extends InnerTests
}

object GeomFx extends CommonJvm
{ def sources = T.sources(T.workspace / "Geom" / "FxSrc")
  def moduleDeps = Seq(Geom)

  def unmanagedClasspath = T {
    import coursier._, parse.DependencyParser
    val fxMod = Dependency(Module(org"org.openjfx", ModuleName("javafx-controls")), "15.0.1")
    val files = Fetch().addDependencies(fxMod).run()
    val pathRefs = files.map(f => PathRef(os.Path(f)))
    Agg(pathRefs: _*)
  }

  def forkArgs = Seq("--module-path",
    sys.env("JAVAFX_HOME") + ":" + "/Users/ajr/Library/Caches/Coursier/v1/https/repo1.maven.org/maven2/org/openjfx/javafx-controls/15.0.1",
    "--add-modules", "javafx.controls"
  )
}

object GeomJs extends CommonJs
{ def moduleDeps = Seq(UtilJs)
  def source2 = T.source(T.workspace / "Geom" / "JsSrc")
  def sources = Geom.sources1() :+ source2()
}

object GeomExs extends CommonJvm
{ def moduleDeps = Seq(Geom)
  def sources1 = T.sources{ Seq("src", "srcLessons").map(name => PathRef(T.workspace / "Geom" / "GeomExs" / name)) }
  def source2 = T.source(T.workspace / "Geom" / "GeomExs" / "JvmSrc")
  def sources = T.sources { sources1() :+ source2() }
  //def mainClass = Some("ostrat.WebPage1")
}

object Tiling extends CommonJvm
{ def moduleDeps = Seq(Geom) 
  def sources1 = T.sources(Seq("src", "srcHex", "srcHLayer", "srcSq", "srcSqLayer").map(name => PathRef(millSourcePath / name)))
  def sources = sources1()
  object test extends InnerTests
}

object TilingJs extends CommonJs
{ def moduleDeps = Seq(GeomJs)
  def sources = Tiling.sources1()
}

object TilingExs extends CommonJvm
{ def moduleDeps = Seq(Tiling)
  def sources = T.sources(T.workspace / "Tiling" / "TilingExs" / "src")
  object test extends InnerTests
  def mainClass = Some("ostrat.pZero.TilingTryApp")
}

object EGrid extends CommonJvm
{ def moduleDeps = Seq(Tiling)
  def sources1 = T.sources(Seq("src", "srcPts").map(name => PathRef(millSourcePath / name)))
  def sources = sources1()
  object test extends InnerTests
}

object EGridJs extends CommonJs
{ def moduleDeps = Seq(TilingJs)
  def sources = EGrid.sources1()
}

object Apps extends CommonJvm
{ def moduleDeps = Seq(EGrid)
  def sources1 = T.sources(Seq("src", "srcStrat").map(name => PathRef(millSourcePath / name)))
  def sources = sources1()
  object test extends InnerTests
}

object Dev extends CommonJvm
{ def moduleDeps = Seq(GeomExs, TilingExs, EGrid, Apps)
  def sources1 = T.sources(millSourcePath / "src")
  def source2 = T.source(millSourcePath / "JvmSrc")
  def sources = T.sources(sources1() :+ source2())
  def resources = T.sources(millSourcePath / "res", millSourcePath / "User")
  //def mainClass = Some("ostrat.pFx.DevApp")
}
/*

object DevJs extends CommonJs
{ def moduleDeps = Seq(WorldJs)
  def sources = T.sources(Dev.millSourcePath / "src", Dev.millSourcePath / "srcJs")
} 
//def run() = Dev.runBackground()
//def test = Geom.test
def jsfast = DevJs.fastLinkJS
def jsfull = DevJs.fullLinkJS*/
