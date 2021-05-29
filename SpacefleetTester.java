package il.ac.tau.cs.sw1.ex9.starfleet;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import il.ac.tau.cs.sw1.ex9.TesterUtil;

public class SpacefleetTester {
    private static int stealthCruiserCount = 0;
    private static final String TESTER_OUTPUT_PATH = "./src/il/ac/tau/cs/sw1/ex9/starfleet/StarfleetManagerTester-Output.txt";
    private static final String TESTER_OUTPUT_ALL_PATH = "./src/il/ac/tau/cs/sw1/ex9/starfleet/StarfleetManagerTester-Output-All.txt";
    private static final String TESTER_WRONG_OUTPUT = "MyOutput.txt";

    private static Stream<Arguments> transportShipsMaintenanceProvider() {
        Set<CrewMember> members = new HashSet<>(Arrays.asList(new Cylon("Jane", 0, 0, 0)));
        return Stream.of(
            Arguments.of(new TransportShip("IFcoltrans1", 2, 1f, members, 0, 0), 3000, 10),
            Arguments.of(new TransportShip("IFcoltrans2", 20, 10f, members, 1, 0), 3005, 10),
            Arguments.of(new TransportShip("IFcoltrans3", 2000, 100f, Collections.emptySet(), 10, 0), 3050, 10),
            Arguments.of(new TransportShip("IFcoltrans4", 20000, 1000f, Collections.emptySet(), 0, 1), 3003, 10),
            Arguments.of(new TransportShip("IFcoltrans5", 200000, 10000f, members, 0, 10), 3030, 10),
            Arguments.of(new TransportShip("IFcoltrans6", 2000000, 100000f, members, 100, 200), 4100, 10)
        );
    }

    private static Stream<Arguments> fightersMaintenanceProvider() {
        Set<CrewMember> members = new HashSet<>(Arrays.asList(new CrewWoman(0, 0, "A"), new Cylon("B", 1, 1, 1)));
        List<Weapon> weapon1 = Arrays.asList(new Weapon("Cap Gun", 1, 1));
        List<Weapon> weapon2 = Arrays.asList(new Weapon("Water Gun", 0, 1), new Weapon("Flash Pistol", 10, 10));
        return Stream.of(
            Arguments.of(new Fighter("Fighter #0", 1, 10000/3333, Collections.emptySet(), Collections.emptyList()), 5500, 10),
            Arguments.of(new Fighter("Fighter #1", 708, 10000/3334, members, weapon1), 4501, 11),
            Arguments.of(new Fighter("Fighter #2", 9999, 10000/3334, Collections.emptySet(), weapon2), 4511, 20),
            Arguments.of(new Fighter("Fighter #3", 9999, 10000/3333, members, weapon2), 5511, 20)
        );
    }

    private static Stream<Arguments> bombersMaintenanceProvider() {
        List<Weapon> weapon1 = Arrays.asList(new Weapon("Molecular Disruption Device", 1000000, 100000));
        List<Weapon> weapon2 = Arrays.asList(new Weapon("Cap Gun", 1, 1));
        Set<CrewMember> members = new HashSet<>(Arrays.asList(new CrewWoman(11, 5, "Andrew Wiggin")));
        return Stream.of(
            Arguments.of(new Bomber("Starfighter #1", 2101, 96700f, members, weapon1, 0), 105000, 1000010),
            Arguments.of(new Bomber("Starfighter #2", 2102, 967000f, Collections.emptySet(), weapon1, 1), 95000, 1000010),
            Arguments.of(new Bomber("Starfighter #3", 2103, 9670f, members, weapon1, 2), 85000, 1000010),
            Arguments.of(new Bomber("Starfighter #4", 2104, 90f, Collections.emptySet(), weapon1, 3), 75000, 1000010),
            Arguments.of(new Bomber("Starfighter #5", 2105, 96000f, Collections.emptySet(), weapon1, 4), 65000, 1000010),
            Arguments.of(new Bomber("Starfighter #6", 2106, 9f, members, weapon1, 5), 55000, 1000010),
            Arguments.of(new Bomber(
                "Starfighter #1 with Cap Gun", 2101, 96700f, members,
                Stream.concat(weapon1.stream(), weapon2.stream()).collect(Collectors.toList()), 0), 105001, 1000011)
        );
    }

    private static Stream<Arguments> stealthCruisersMaintenanceProvider1() {
        Set<CrewMember> members = new HashSet<>(Arrays.asList(new CrewWoman(0, 0, "C")));
        List<Weapon> weapon = Arrays.asList(new Weapon("Cap Gun", 1, 1));
        stealthCruiserCount += 3;
        return Stream.of(
            Arguments.of(new StealthCruiser("StealthCruisers #0", 1, 10000/3333, Collections.emptySet(), Collections.emptyList()), 5500 + stealthCruiserCount * 50, 10),
            Arguments.of(new StealthCruiser("StealthCruisers #1", 708, 10000/3334, members, weapon), 4501 + stealthCruiserCount * 50, 11),
            Arguments.of(new StealthCruiser("StealthCruisers #2", 9999, 10000/3334, members, Collections.emptyList()), 4500 + stealthCruiserCount * 50, 10)
        );
    }

    private static Stream<Arguments> stealthCruisersMaintenanceProvider2() {
        stealthCruiserCount++;
        return Stream.of(
            Arguments.of(new StealthCruiser("StealthCruisers #3", 1, 5, Collections.emptySet(), Collections.emptyList()), 7500 + stealthCruiserCount * 50, 10)
        );
    }

    private static Stream<Arguments> colonialViperMaintenanceProvider() {
        Set<CrewWoman> members = new HashSet<>(Arrays.asList(new CrewWoman(1, 10, "Isabella Garcia-Shapiro"), (CrewWoman)new Officer("", 0, 0, OfficerRank.LieutenantCommander)));
        List<Weapon> weapon = Arrays.asList(new Weapon("Super Cute Weapon", 1570796, 90));

        return Stream.of(
            Arguments.of(new ColonialViper("ColonialViper #0", 1, 10000/3333, Collections.emptySet(), Collections.emptyList()), 5500, 10),
            Arguments.of(new ColonialViper("ColonialViper #1", 708, 10000/3334, Collections.emptySet(), Collections.emptyList()), 5000, 10),
            Arguments.of(new ColonialViper("ColonialViper #2", 78, 10, Collections.emptySet(), Collections.emptyList()), 9000, 10),
            Arguments.of(new ColonialViper("ColonialViper #3", 99, 10, members, Collections.emptyList()), 10000, 10),
            Arguments.of(new ColonialViper("ColonialViper #4", 666, 10, members, weapon), 10090, 1570806)
        );
    }

    private static Stream<Arguments> cylonRaiderMaintenanceProvider() {
        Set<Cylon> members = new HashSet<>(Arrays.asList(new Cylon("Android 16", 0, 0, 16), new Cylon("Android 17", 0, 0, 17), new Cylon("Android 18", 0, 0, 18)));
        List<Weapon> weapon = Arrays.asList(
            new Weapon("Android Bomb", 50000, 1000),
            new Weapon("Hell's Beam", 50100, 1100),
            new Weapon("Hell's Flash", 50200, 1200),
            new Weapon("Full Power Hell Flash", 50300, 1300),
            new Weapon("Self Destruct Device", 1000000, 10000));

        return Stream.of(
            Arguments.of(new CylonRaider("CylonRaider #0", 1, 10000/3333, Collections.emptySet(), Collections.emptyList()), 7100, 10),
            Arguments.of(new CylonRaider("CylonRaider #1", 708, 10000/3334, Collections.emptySet(), Collections.emptyList()), 5900, 10),
            Arguments.of(new CylonRaider("CylonRaider #2", 78, 10, Collections.emptySet(), Collections.emptyList()), 15500, 10),
            Arguments.of(new CylonRaider("CylonRaider #3", 99, 10, members, Collections.emptyList()), 17000, 10),
            Arguments.of(new CylonRaider("CylonRaider #4", 666, 10, members, weapon.subList(0, 1)), 18000, 50010),
            Arguments.of(new CylonRaider("CylonRaider #5", 666, 10, members, weapon.subList(0, 2)), 19100, 100110),
            Arguments.of(new CylonRaider("CylonRaider #6", 666, 10, members, weapon.subList(0, 3)), 20300, 150310),
            Arguments.of(new CylonRaider("CylonRaider #7", 666, 10, members, weapon.subList(0, 4)), 21600, 200610),
            Arguments.of(new CylonRaider("CylonRaider #8", 666, 10, members, weapon), 31600, 1200610)
        );
    }

    @ParameterizedTest
    @MethodSource({
        "transportShipsMaintenanceProvider",
        "fightersMaintenanceProvider",
        "bombersMaintenanceProvider",
        "stealthCruisersMaintenanceProvider1",
        "stealthCruisersMaintenanceProvider2",
        "colonialViperMaintenanceProvider",
        "cylonRaiderMaintenanceProvider"
    })
    public void testBomberTechnicianReduction(Spaceship spaceship, int expectedMaintenance, int expectedFirePower) {
        assertEquals(
            expectedMaintenance,
            spaceship.getAnnualMaintenanceCost(),
            "Maintenance - " + spaceship.getName()
        );
        assertEquals(
            expectedFirePower,
            spaceship.getFirePower(),
            "Fire Power - " + spaceship.getName()
        );
    }

    @ParameterizedTest
    @MethodSource({
        "transportShipsMaintenanceProvider",
        "fightersMaintenanceProvider",
        "bombersMaintenanceProvider",
        "stealthCruisersMaintenanceProvider1",
        "stealthCruisersMaintenanceProvider2",
        "colonialViperMaintenanceProvider",
        "cylonRaiderMaintenanceProvider"
    })
    public void testImmutability(Spaceship spaceship) {
        int maintenanceBefore = spaceship.getAnnualMaintenanceCost();
        int firepowerBefore = spaceship.getFirePower();
        String reprBefore = spaceship.toString();

        Set<? extends CrewMember> crewBefore = spaceship.getCrewMembers();
        Set<? extends CrewMember> crewToClear = spaceship.getCrewMembers();
        crewToClear.clear();
        Set<? extends CrewMember> crewAfter = spaceship.getCrewMembers();

        assertTrue(
            crewBefore.equals(crewAfter),
            String.format("%s crew is not immutable", spaceship.getName())
        );

        assertEquals(
            maintenanceBefore, 
            spaceship.getAnnualMaintenanceCost(),
            spaceship.getName()
        );

        assertEquals(
            firepowerBefore, 
            spaceship.getFirePower(),
            spaceship.getName()
        );

        assertTrue(reprBefore.equals(spaceship.toString()));
    }

    @ParameterizedTest
    @MethodSource({
        "fightersMaintenanceProvider",
        "bombersMaintenanceProvider",
        "stealthCruisersMaintenanceProvider1",
        "stealthCruisersMaintenanceProvider2",
        "colonialViperMaintenanceProvider",
        "cylonRaiderMaintenanceProvider"
    })
    public void testWeaponImmutability(Spaceship spaceship) {
        assertDoesNotThrow(() -> {
            List<Weapon> weaponBefore = (List<Weapon>)spaceship.getClass().getMethod("getWeapon").invoke(spaceship);
            List<Weapon> weaponToClear = (List<Weapon>)spaceship.getClass().getMethod("getWeapon").invoke(spaceship);
            weaponToClear.clear();
            List<Weapon> weaponAfter = (List<Weapon>)spaceship.getClass().getMethod("getWeapon").invoke(spaceship);

            assertTrue(
                weaponBefore.equals(weaponAfter),
                String.format("%s weapon is not immutable", spaceship.getName())
            );
        });
    }

    @Test
    public void testTeachingAssistantsTest() throws IOException {
        Path path = Paths.get(stealthCruiserCount == 0 ? TESTER_OUTPUT_PATH : TESTER_OUTPUT_ALL_PATH);
        String output = TesterUtil.testOutput(StarfleetManagerTester::main, new String[]{ }, "StarfleetManagerTester.main");
        String expected = new String(
            Files.readAllBytes(
                Paths.get(stealthCruiserCount == 0 ? TESTER_OUTPUT_PATH : TESTER_OUTPUT_ALL_PATH)), 
                StandardCharsets.UTF_8
        );

        output = output.replaceAll("\r\n", "\n");
        expected = expected.replaceAll("\r\n", "\n");

        if (!expected.equals(output)) {
            try (FileWriter writer = new FileWriter(TESTER_WRONG_OUTPUT)) {
                writer.write(output);
            }
            assert false : String.format(
                "Your output and the expeced output do not match. Writing your result into a file. %n"
                + "You can compare the results by running the following command (red - expected, green - yours):%n"
                + "git --no-pager diff --no-index --ignore-space-at-eol %s %s %n%n"
                + "Afterwards, please delete the file by running (on a Linux machine):%n"
                + "rm %s%n"
                + "or (on a Windows machine):%n"
                + "del /f %s",
                path.toAbsolutePath().toString(),
                Paths.get(TESTER_WRONG_OUTPUT).toAbsolutePath().toString(),
                Paths.get(TESTER_WRONG_OUTPUT).toAbsolutePath().toString(),
                Paths.get(TESTER_WRONG_OUTPUT).toAbsolutePath().toString()
            );
        }
    }
}
