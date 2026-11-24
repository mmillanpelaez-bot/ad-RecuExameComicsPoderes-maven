import config.HibernateConfig;
import model.Autor;
import model.Comic;
import model.PoderUnico;
import model.Protagonista;
import service.ServicioAutor;
import service.ServicioComic;
import service.ServicioPoderUnico;
import service.ServicioProtagonista;
import util.FicheirosUtil;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        ServicioComic servicioComic = new ServicioComic();
        ServicioAutor servicioAutor = new ServicioAutor();
        ServicioProtagonista servicioProtagonista = new ServicioProtagonista();
        ServicioPoderUnico servicioPoderUnico = new ServicioPoderUnico();

        // 1. Crear obxectos de Autores, Protagonistas e Comics e inserilos mediante ServicioComic
        Autor stanLee = new Autor("Stan", "Lee", "estadounidense");
        Autor alanMoore = new Autor("Alan", "Moore", "británica");
        Autor frankMiller = new Autor("Frank", "Miller", "estadounidense");
        Autor hergeRemi = new Autor("Hergé", "Remi", "belga");

        Protagonista spiderMan = new Protagonista("Spider-Man");
        Protagonista batman = new Protagonista("Batman");
        Protagonista superman = new Protagonista("Superman");
        Protagonista wonderWoman = new Protagonista("Wonder Woman");
        Protagonista tintin = new Protagonista("Tintín");
        Protagonista daredevil = new Protagonista("Daredevil");

        servicioComic.insertComic(new Comic("The Amazing Spider-Man", 1963, stanLee, spiderMan));
        servicioComic.insertComic(new Comic("Batman: The Killing Joke", 1988, alanMoore, batman));
        servicioComic.insertComic(new Comic("Superman: The Man of Steel", 1986, frankMiller, superman));
        servicioComic.insertComic(new Comic("Wonder Woman: Gods and Mortals", 1987, alanMoore, wonderWoman));
        servicioComic.insertComic(new Comic("The Adventures of Tintin", 1929, hergeRemi, tintin));
        servicioComic.insertComic(new Comic("Daredevil: Born Again", 1986, stanLee, daredevil));
        servicioComic.insertComic(new Comic("Batman: Year One", 1987, frankMiller, batman));
        servicioComic.insertComic(new Comic("Spider-Man: Blue", 2002, stanLee, spiderMan));

        // 2. Obter e mostrar listado dos protagonistas e os seus ids (a través dos comics lidos)
        List<Comic> comics = servicioComic.readComics();
        Map<String, Protagonista> protagonistasPorNome = new LinkedHashMap<>();
        for (Comic c : comics) {
            Protagonista p = c.getProtagonista();
            protagonistasPorNome.putIfAbsent(p.getNome(), p);
        }

        System.out.println("=== LISTADO DE PROTAGONISTAS E OS SEUS IDS ===");
        for (Protagonista p : protagonistasPorNome.values()) {
            System.out.println("ID: " + p.getId() + " - " + p.getNome());
        }

        // 3. Inserir poderes únicos asociados ó seu protagonista mediante id
        Protagonista pSpiderMan = protagonistasPorNome.get("Spider-Man");
        Protagonista pBatman = protagonistasPorNome.get("Batman");
        Protagonista pSuperman = protagonistasPorNome.get("Superman");
        Protagonista pWonderWoman = protagonistasPorNome.get("Wonder Woman");
        Protagonista pTintin = protagonistasPorNome.get("Tintín");
        Protagonista pDaredevil = protagonistasPorNome.get("Daredevil");

        servicioPoderUnico.insertPoderUnico(new PoderUnico("Lanzamento de telarañas", pSpiderMan));
        servicioPoderUnico.insertPoderUnico(new PoderUnico("Sentido arácnido", pSpiderMan));
        servicioPoderUnico.insertPoderUnico(new PoderUnico("Diñeiro", pBatman));
        servicioPoderUnico.insertPoderUnico(new PoderUnico("Detectivismo avanzado", pBatman));
        servicioPoderUnico.insertPoderUnico(new PoderUnico("Visión calorífica", pSuperman));
        servicioPoderUnico.insertPoderUnico(new PoderUnico("Alento xélido", pSuperman));
        servicioPoderUnico.insertPoderUnico(new PoderUnico("Lazo da Verdade", pWonderWoman));
        servicioPoderUnico.insertPoderUnico(new PoderUnico("Brazaletes indestrutibles", pWonderWoman));
        servicioPoderUnico.insertPoderUnico(new PoderUnico("Intelixencia investigadora", pTintin));
        servicioPoderUnico.insertPoderUnico(new PoderUnico("Ecolocalización por radar", pDaredevil));

        // 4. Listar os contidos das táboas seguindo o formato especificado
        System.out.println("\n=== CONTIDO INICIAL DAS TÁBOAS ===");
        comics = servicioComic.readComics();
        listarContidos(comics);

        // 5. [Recu Ficheiros] Ler nome do autor do ficheiro "datos" e actualizar "Stan Lee" -> "Satan Leee"
        List<String> datosFicheiro = FicheirosUtil.readFicheiro("datos");
        if (datosFicheiro.size() >= 2) {
            String nomeAutorBuscar = datosFicheiro.get(0); // "Stan Lee"
            Autor autorActualizar = null;
            for (Comic c : comics) {
                Autor a = c.getAutor();
                String nomeCompleto = a.getNome() + " " + a.getApelidos();
                if (nomeCompleto.equals(nomeAutorBuscar)) {
                    autorActualizar = a;
                    break;
                }
            }

            if (autorActualizar != null) {
                autorActualizar.setNome("Satan");
                autorActualizar.setApelidos("Leee");
                servicioAutor.updateAutor(autorActualizar);

                // 6. [Recu Serialización] Serializar o obxecto autor actualizado en DB
                FicheirosUtil.serializarAutor(autorActualizar, "autor_actualizado.dat");

                // 7. [Recu XML] Exportar a XML os datos do autor actualizado en DB
                FicheirosUtil.exportarAutorXML(autorActualizar, "autor_actualizado.xml");
            }

            // 8. [Recu Ficheiros] Ler nome do protagonista (2ª liña de "datos") e actualizar "Spider-Man" -> "Spooder-Mon"
            String nomeProtaBuscar = datosFicheiro.get(1); // "Spider-Man"
            Protagonista protaActualizar = null;
            for (Comic c : comics) {
                Protagonista p = c.getProtagonista();
                if (p.getNome().equals(nomeProtaBuscar)) {
                    protaActualizar = p;
                    break;
                }
            }

            if (protaActualizar != null) {
                protaActualizar.setNome("Spooder-Mon");
                servicioProtagonista.updateProtagonista(protaActualizar);
            }
        }

        // 9. Listar os contidos das táboas actualizadas
        System.out.println("\n=== CONTIDO DAS TÁBOAS ACTUALIZADAS ===");
        comics = servicioComic.readComics();
        listarContidos(comics);

        // 10. Borrar o contido de tódalas táboas (respeitando claves foráneas)
        Set<PoderUnico> poderesBorrar = new LinkedHashSet<>();
        Set<Autor> autoresBorrar = new LinkedHashSet<>();
        Set<Protagonista> protasBorrar = new LinkedHashSet<>();

        for (Comic c : comics) {
            autoresBorrar.add(c.getAutor());
            protasBorrar.add(c.getProtagonista());
            if (c.getProtagonista().getPoderesUnicos() != null) {
                poderesBorrar.addAll(c.getProtagonista().getPoderesUnicos());
            }
        }

        for (PoderUnico pu : poderesBorrar) {
            servicioPoderUnico.deletePoderUnico(pu);
        }
        for (Comic c : comics) {
            servicioComic.deleteComic(c);
        }
        for (Autor a : autoresBorrar) {
            servicioAutor.deleteAutor(a);
        }
        for (Protagonista p : protasBorrar) {
            servicioProtagonista.deleteProtagonista(p);
        }

        // 11. Listar o contido de tódalas táboas (debería ser baleiro)
        System.out.println("\n=== CONTIDO DESPOIS DO BORRADO ===");
        comics = servicioComic.readComics();
        listarContidos(comics);

        HibernateConfig.getSessionFactory().close();
    }

    private static void listarContidos(List<Comic> comics) {
        if (comics == null || comics.isEmpty()) {
            System.out.println("As táboas están baleiras.");
            return;
        }

        Map<Autor, List<Comic>> comicsPorAutor = new LinkedHashMap<>();
        for (Comic c : comics) {
            comicsPorAutor.computeIfAbsent(c.getAutor(), k -> new ArrayList<>()).add(c);
        }

        for (Map.Entry<Autor, List<Comic>> entry : comicsPorAutor.entrySet()) {
            Autor autor = entry.getKey();
            System.out.println("--------------------------------------------------------------------------------------------------");
            System.out.println("Autor: " + autor.getNome() + " " + autor.getApelidos());
            System.out.println("Comics:");
            for (Comic c : entry.getValue()) {
                System.out.println(c.getTitulo() + " (" + c.getAno() + ")");
                Protagonista p = c.getProtagonista();
                System.out.println("    Protagonista: " + p.getNome());
                System.out.println("    Poderes únicos:");
                if (p.getPoderesUnicos() != null) {
                    for (PoderUnico pu : p.getPoderesUnicos()) {
                        System.out.println("        - " + pu.getNomePoder());
                    }
                }
            }
            System.out.println("--------------------------------------------------------------------------------------------------");
        }
    }
}
