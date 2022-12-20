import cats.effect.{ExitCode, IO}
import cats.implicits.{catsSyntaxTuple3Semigroupal, toTraverseOps}
import com.google.common.base.Charsets
import com.google.common.hash.Hashing
import com.monovore.decline.Opts
import com.monovore.decline.effect.CommandIOApp

import java.io.PrintWriter
import java.nio.file.{Path, Paths}
import scala.io.Source
import scala.util.control.NonFatal

object CryptographyApp extends CommandIOApp(
  name = "pure-cryptos",
  header = "Pure app to encode student task number by full name.",
  version = "0.0.1"
) {
  override def main: Opts[IO[ExitCode]] = {
    val pathOpts =
      Opts.option[Path](long = "file", help = "Name of input file.", short = "f")
        .orElse(Opts(Paths.get("input.txt")))
    val numOpts =
      Opts.option[Int](long = "numbilets ", help = "Number of tickets.", short = "n").orElse(Opts(20))
    val paramOpts =
      Opts.option[Int](long = "parameter", help = "Distribution parameter (Salt).", short = "p").orElse(Opts(42))

    (pathOpts, numOpts, paramOpts).mapN { (path, num, param) =>
      (for {
        names <- readStudents(path)
        tickets <- getTickets(names, num, param)
        _ <- showStudents(names zip tickets)
      } yield ExitCode.Success)
        .recover {
          case NonFatal(_) => ExitCode.Error
        }
    }
  }

  private def readStudents(path: Path): IO[List[String]] =
    IO.delay(Source.fromFile(path.toFile))
      .bracket { source =>
        IO.delay(source.getLines().toList)
      } { source =>
        IO.delay(source.close())
      }

  private def getTickets(names: List[String], num: Int, salt: Int): IO[List[BigInt]] =
    IO.pure(Hashing.sha256()).flatMap(hash =>
      names.traverse { fullName =>
        IO.delay {
          val sha = hash.newHasher()
            .putInt(salt)
            .putString(fullName, Charsets.UTF_8)
            .hash()

          toUnsigned(sha.asLong()) % num + 1
        }
      })

  private def toUnsigned(n: Long): BigInt =
    (BigInt(n >>> 1) << 1) + (n & 1)

  private def showStudents(students: List[(String, BigInt)]): IO[Unit] =
    IO.delay(new PrintWriter(System.out))
      .bracket { out =>
        students.traverse { case (fillName, crypt) =>
          IO.delay(out.println(s"$fillName: $crypt"))
        }
      } { out =>
        IO.delay(out.close())
      }.as(())
}
