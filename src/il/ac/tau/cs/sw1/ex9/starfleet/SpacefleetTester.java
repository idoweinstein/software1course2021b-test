package il.ac.tau.cs.sw1.ex9.starfleet;

import il.ac.tau.cs.sw1.ex9.TesterUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SpacefleetTester {
  private static final String FOLDER_PATH = "./src/il/ac/tau/cs/sw1/ex9/starfleet/";
  private static final String TESTER_OUTPUT_PATH =
      FOLDER_PATH + "StarfleetManagerTester-Output.txt";
  private static final String TESTER_OUTPUT_ALL_PATH =
      FOLDER_PATH + "StarfleetManagerTester-Output-All.txt";
  private static final String TESTER_WRONG_OUTPUT = FOLDER_PATH + "MyOutput.txt";
  private static final int COST_PER_STEALTH_CRUISER = 50;
  private static final List<StealthCruiser> stealthCruisers = new ArrayList<>();
  private static int stealthCruiserCount = 0;
  private static final Supplier<Integer> addedCostPerStealthCruiserUnit =
      () -> stealthCruiserCount * COST_PER_STEALTH_CRUISER;

  @SuppressWarnings("unused")
  private static Stream<Arguments> transportShipsMaintenanceProvider() {
    Set<CrewMember> members = Collections.singleton(new Cylon("Jane", 0, 0, 0));
    return Stream.of(
        Arguments.of(new TransportShip("IFcoltrans1", 2, 0f, members, 0, 0), 3000, 10),
        Arguments.of(new TransportShip("IFcoltrans2", 20, 2.5f, members, 1, 0), 3005, 10),
        Arguments.of(
            new TransportShip("IFcoltrans3", 2000, 3.7f, Collections.emptySet(), 10, 0), 3050, 10),
        Arguments.of(
            new TransportShip("IFcoltrans4", 20000, 5.1f, Collections.emptySet(), 0, 1), 3003, 10),
        Arguments.of(new TransportShip("IFcoltrans5", 200000, 7.6f, members, 0, 10), 3030, 10),
        Arguments.of(new TransportShip("IFcoltrans6", 2000000, 10f, members, 100, 200), 4100, 10));
  }

  @SuppressWarnings("unused")
  private static Stream<Arguments> fightersMaintenanceProvider() {
    Set<CrewMember> members =
        new HashSet<>(
            Arrays.asList(
                new CrewWoman(0, 0, "Marvin the Paranoid Android"), new Cylon("V", 1, 1, 1)));
    List<Weapon> weapon1 = Collections.singletonList(new Weapon("Cap Gun", 1, 1));
    List<Weapon> weapon2 =
        Arrays.asList(new Weapon("Water Gun", 0, 1), new Weapon("Flash Pistol", 10, 10));
    return Stream.of(
        Arguments.of(
            new Fighter(
                "Fighter #0", 1, 10000 / 3333f, Collections.emptySet(), Collections.emptyList()),
            5500,
            10),
        Arguments.of(new Fighter("Fighter #1", 708, 10000 / 3334f, members, weapon1), 5500, 11),
        Arguments.of(
            new Fighter("Fighter #2", 9999, 10000 / 3334f, Collections.emptySet(), weapon2),
            5510,
            20),
        Arguments.of(new Fighter("Fighter #3", 9999, 10000 / 3333f, members, weapon2), 5511, 20));
  }

  @SuppressWarnings("unused")
  private static Stream<Arguments> bombersMaintenanceProvider() {
    List<Weapon> weapon1 =
        Collections.singletonList(new Weapon("Molecular Disruption Device", 1000000, 100000));
    List<Weapon> weapon2 = Collections.singletonList(new Weapon("Cap Gun", 1, 1));
    Set<CrewMember> members = Collections.singleton(new CrewWoman(11, 5, "Andrew Wiggin"));
    List<Weapon> combinedWeapons =
        Stream.concat(weapon1.stream(), weapon2.stream()).collect(Collectors.toList());
    return Stream.of(
        Arguments.of(new Bomber("Starfighter #1", 2101, 0f, members, weapon1, 0), 105000, 1000010),
        Arguments.of(
            new Bomber("Starfighter #2", 2102, 1f, Collections.emptySet(), weapon1, 1),
            95000,
            1000010),
        Arguments.of(new Bomber("Starfighter #3", 2103, 2f, members, weapon1, 2), 85000, 1000010),
        Arguments.of(
            new Bomber("Starfighter #4", 2104, 3f, Collections.emptySet(), weapon1, 3),
            75000,
            1000010),
        Arguments.of(
            new Bomber("Starfighter #5", 2105, 4f, Collections.emptySet(), weapon1, 4),
            65000,
            1000010),
        Arguments.of(new Bomber("Starfighter #6", 2106, 5f, members, weapon1, 5), 55000, 1000010),
        Arguments.of(
            new Bomber("Starfighter #1 with Cap Gun", 2101, 0f, members, combinedWeapons, 0),
            105001,
            1000011),
        Arguments.of(
            new Bomber("Starfighter #2", 2102, 1f, Collections.emptySet(), combinedWeapons, 1),
            95000,
            1000011));
  }

  @SuppressWarnings("unused")
  private static Stream<Arguments> stealthCruisersMaintenanceProvider1() {
    Set<CrewMember> members = Collections.singleton(new CrewWoman(0, 0, "C"));
    List<Weapon> weapon = Collections.singletonList(new Weapon("Cap Gun", 1, 1));

    // TODO Move initialisation to BeforeAll
    if (stealthCruisers.size() == 0) {
      stealthCruisers.add(
          new StealthCruiser(
              "StealthCruisers #0",
              1,
              10000 / 3333f,
              Collections.emptySet(),
              Collections.emptyList()));
      stealthCruisers.add(
          new StealthCruiser("StealthCruisers #1", 708, 10000 / 3334f, members, weapon));
      stealthCruisers.add(
          new StealthCruiser(
              "StealthCruisers #2", 9999, 10000 / 3334f, members, Collections.emptyList()));
      stealthCruiserCount += 3;
    }

    return Stream.of(
        Arguments.of(stealthCruisers.get(0), 5500 + addedCostPerStealthCruiserUnit.get(), 10),
        Arguments.of(stealthCruisers.get(1), 5500 + addedCostPerStealthCruiserUnit.get(), 11),
        Arguments.of(stealthCruisers.get(2), 5499 + addedCostPerStealthCruiserUnit.get(), 10));
  }

  @SuppressWarnings("unused")
  private static Stream<Arguments> stealthCruisersMaintenanceProvider2() {
    stealthCruiserCount++;
    return Stream.of(
        Arguments.of(
            new StealthCruiser(
                "StealthCruisers #3", 1, 5, Collections.emptySet(), Collections.emptyList()),
            7500 + addedCostPerStealthCruiserUnit.get(),
            10));
  }

  @SuppressWarnings("unused")
  private static Stream<Arguments> colonialViperMaintenanceProvider() {
    Set<CrewWoman> members =
        new HashSet<>(
            Arrays.asList(
                new CrewWoman(1, 10, "Isabella Garcia-Shapiro"),
                new Officer("", 0, 0, OfficerRank.LieutenantCommander)));
    List<Weapon> weapon = Collections.singletonList(new Weapon("Super Cute Weapon", 1570796, 90));

    return Stream.of(
        Arguments.of(
            new ColonialViper(
                "ColonialViper #0",
                1,
                10000 / 3333f,
                Collections.emptySet(),
                Collections.emptyList()),
            5500,
            10),
        Arguments.of(
            new ColonialViper(
                "ColonialViper #1",
                708,
                10000 / 3334f,
                Collections.emptySet(),
                Collections.emptyList()),
            5499,
            10),
        Arguments.of(
            new ColonialViper(
                "ColonialViper #2", 78, 10, Collections.emptySet(), Collections.emptyList()),
            9000,
            10),
        Arguments.of(
            new ColonialViper("ColonialViper #3", 99, 10, members, Collections.emptyList()),
            10000,
            10),
        Arguments.of(
            new ColonialViper("ColonialViper #4", 666, 10, members, weapon), 10090, 1570806));
  }

  @SuppressWarnings("unused")
  private static Stream<Arguments> cylonRaiderMaintenanceProvider() {
    Set<Cylon> members =
        new HashSet<>(
            Arrays.asList(
                new Cylon("Android 10", 0, 0, 10),
                new Cylon("Android 11", 0, 0, 11),
                new Cylon("Android 42", 42, 42, 12)));
    List<Weapon> weapon =
        Arrays.asList(
            new Weapon("Android Bomb", 50000, 1000),
            new Weapon("Hell's Beam", 50100, 1100),
            new Weapon("Hell's Flash", 50200, 1200),
            new Weapon("Galaxy Node S7", 50300, 1300),
            new Weapon("Self Destruct Device", 1000000, 10000));

    return Stream.of(
        Arguments.of(
            new CylonRaider(
                "CylonRaider #0",
                1,
                10000 / 3333f,
                Collections.emptySet(),
                Collections.emptyList()),
            7100,
            10),
        Arguments.of(
            new CylonRaider(
                "CylonRaider #1",
                708,
                10000 / 3334f,
                Collections.emptySet(),
                Collections.emptyList()),
            7099,
            10),
        Arguments.of(
            new CylonRaider(
                "CylonRaider #2", 78, 10, Collections.emptySet(), Collections.emptyList()),
            15500,
            10),
        Arguments.of(
            new CylonRaider("CylonRaider #3", 99, 10, members, Collections.emptyList()), 17000, 10),
        Arguments.of(
            new CylonRaider("CylonRaider #4", 666, 10, members, weapon.subList(0, 1)),
            18000,
            50010),
        Arguments.of(
            new CylonRaider("CylonRaider #5", 666, 10, members, weapon.subList(0, 2)),
            19100,
            100110),
        Arguments.of(
            new CylonRaider("CylonRaider #6", 666, 10, members, weapon.subList(0, 3)),
            20300,
            150310),
        Arguments.of(
            new CylonRaider("CylonRaider #7", 666, 10, members, weapon.subList(0, 4)),
            21600,
            200610),
        Arguments.of(new CylonRaider("CylonRaider #8", 666, 10, members, weapon), 31600, 1200610));
  }

  @SuppressWarnings("unused")
  private static Stream<Arguments> testContainedMethodsProvider() {
    List<String> crewMemberMethods = Arrays.asList("getName", "getAge", "getYearsInService");

    List<String> officerMethods = new ArrayList<>(crewMemberMethods);
    officerMethods.add("getRank");

    List<String> cylonMethods = new ArrayList<>(crewMemberMethods);
    cylonMethods.add("getModelNumber");

    List<String> spaceshipMethods =
        Arrays.asList(
            "getName",
            "getCommissionYear",
            "getMaximalSpeed",
            "getFirePower",
            "getCrewMembers",
            "getAnnualMaintenanceCost");

    List<String> transportShipMethods = new ArrayList<>(spaceshipMethods);
    transportShipMethods.add("getCargoCapacity");
    transportShipMethods.add("getPassengerCapacity");

    List<String> fighterMethods = new ArrayList<>(spaceshipMethods);
    fighterMethods.add("getWeapon");
    fighterMethods.add("getFirePower");

    List<String> bomberMethods = new ArrayList<>(fighterMethods);
    bomberMethods.add("getNumberOfTechnicians");

    return Stream.of(
        Arguments.of(CrewMember.class, crewMemberMethods),
        Arguments.of(CrewWoman.class, crewMemberMethods),
        Arguments.of(Officer.class, officerMethods),
        Arguments.of(Cylon.class, cylonMethods),
        Arguments.of(Spaceship.class, spaceshipMethods),
        Arguments.of(TransportShip.class, transportShipMethods),
        Arguments.of(Fighter.class, fighterMethods),
        Arguments.of(Bomber.class, bomberMethods),
        Arguments.of(StealthCruiser.class, fighterMethods),
        Arguments.of(ColonialViper.class, fighterMethods),
        Arguments.of(CylonRaider.class, fighterMethods));
  }

  @ParameterizedTest
  @MethodSource("testContainedMethodsProvider")
  public void testContainedMethods(Class<?> clazz, List<String> expectedMethods) {
    List<String> methods =
        Arrays.stream(clazz.getMethods()).map(Method::getName).collect(Collectors.toList());
    expectedMethods.forEach(
        method ->
            assertTrue(
                methods.contains(method),
                String.format(
                    "The method '%s' is missing from %s", method, clazz.getSimpleName())));
  }

  @Test
  public void testCrewWomanImplementsCrewMember() {
    CrewWoman crewWoman = new CrewWoman(66, 48, "Amanda Ripley");
    //noinspection ConstantConditions
    assertTrue(crewWoman instanceof CrewMember);
  }

  @Test
  public void testOfficerImplementsCrewMember() {
    Officer officer = new Officer("Sarah Briggs", 30, 10, OfficerRank.Commander);
    //noinspection ConstantConditions
    assertTrue(officer instanceof CrewMember);
  }

  @Test
  public void testCylonImplementsCrewMember() {
    Cylon cylon = new Cylon("John Lumic", 50, 0, 0);
    //noinspection ConstantConditions
    assertTrue(cylon instanceof CrewMember);
  }

  @Test
  public void testTransportShipImplementsSpaceship() {
    TransportShip transportShip =
        new TransportShip("GTT Elysium", 2321, 4.5f, Collections.emptySet(), 40000, 20000);
    //noinspection ConstantConditions
    assertTrue(transportShip instanceof Spaceship);
  }

  @Test
  public void testFighterImplementsSpaceship() {
    Fighter fighter =
        new Fighter("Arwing", 2167, 4.2f, Collections.emptySet(), Collections.emptyList());
    //noinspection ConstantConditions
    assertTrue(fighter instanceof Spaceship);
  }

  @Test
  public void testBomberImplementsSpaceship() {
    Bomber bomber =
        new Bomber(
            "MG-100 StarFortress SF-17",
            3154,
            0.3f,
            Collections.emptySet(),
            Collections.emptyList(),
            3);
    //noinspection ConstantConditions
    assertTrue(bomber instanceof Spaceship);
  }

  @Test
  public void testStealthCruiserImplementsSpaceship() {
    stealthCruiserCount++;
    StealthCruiser stealthCruiser =
        new StealthCruiser(
            "Normandy", 2183, 2.98f, Collections.emptySet(), Collections.emptyList());
    //noinspection ConstantConditions
    assertTrue(stealthCruiser instanceof Spaceship);
  }

  @Test
  public void testColonialViperImplementsSpaceship() {
    ColonialViper colonialViper =
        new ColonialViper(
            "Eurofighter Typhoon", 15, 0.1f, Collections.emptySet(), Collections.emptyList());
    //noinspection ConstantConditions
    assertTrue(colonialViper instanceof Spaceship);
  }

  @Test
  public void testCylonRaiderImplementsSpaceship() {
    CylonRaider cylonRaider =
        new CylonRaider(
            "cba to find any cool names",
            Integer.MIN_VALUE,
            0f,
            Collections.emptySet(),
            Collections.emptyList());

    //noinspection ConstantConditions
    assertTrue(cylonRaider instanceof Spaceship);
  }

  // TODO Add a test for every function

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
  public void testAnnualMaintenanceCostAndFirePower(
      Spaceship spaceship, int expectedMaintenance, int expectedFirePower) {
    assertEquals(
        expectedMaintenance,
        spaceship.getAnnualMaintenanceCost(),
        "Maintenance - " + spaceship.getName());
    assertEquals(
        expectedFirePower, spaceship.getFirePower(), "Fire Power - " + spaceship.getName());
  }

  @Disabled
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
    try {
      crewToClear.clear();
    } catch (UnsupportedOperationException ignored) {
        // Do nothing - expected behavior
    }
    Set<? extends CrewMember> crewAfter = spaceship.getCrewMembers();

    assertEquals(
        crewBefore, crewAfter, String.format("%s crew is not immutable", spaceship.getName()));

    assertEquals(maintenanceBefore, spaceship.getAnnualMaintenanceCost(), spaceship.getName());

    assertEquals(firepowerBefore, spaceship.getFirePower(), spaceship.getName());

    assertEquals(reprBefore, spaceship.toString());
  }

  @Disabled
  @SuppressWarnings("unchecked")
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
    String method = "getWeapon";

    try {
      Method getWeaponMethod = spaceship.getClass().getMethod(method);
      List<Weapon> weaponBefore = (List<Weapon>) getWeaponMethod.invoke(spaceship);
      List<Weapon> weaponToClear = (List<Weapon>) getWeaponMethod.invoke(spaceship);
      weaponToClear.clear();
      List<Weapon> weaponAfter = (List<Weapon>) getWeaponMethod.invoke(spaceship);
      assertEquals(
          weaponBefore,
          weaponAfter,
          String.format("%s weapon is not immutable", spaceship.getName()));
    } catch (IllegalAccessException e) {
      Assertions.fail(String.format("The method '%s' is inaccessible", method));
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      Assertions.fail(String.format("'%s' threw an exception while being invoked", method));
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      Assertions.fail(
          String.format(
              "The method '%s' does not exist in %s",
              method, spaceship.getClass().getSimpleName()));
      e.printStackTrace();
    } catch (UnsupportedOperationException ignored) {
        // Do nothing - expected behavior
    }
  }

  @Test
  public void testTeachingAssistantsTest() throws IOException {
    Path path = Paths.get(stealthCruiserCount == 0 ? TESTER_OUTPUT_PATH : TESTER_OUTPUT_ALL_PATH);

    String output =
        TesterUtil.testOutput(
            StarfleetManagerTester::main, new String[] {}, "StarfleetManagerTester.main");
    stealthCruiserCount += 3;
    String expected = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);

    output = TesterUtil.crlfToLf(output);
    expected = TesterUtil.crlfToLf(expected);

    if (!expected.equals(output)) {
      try (FileWriter writer = new FileWriter(TESTER_WRONG_OUTPUT)) {
        writer.write(output);
      }
      // Using assertEquals instead of fail, since IntelliJ allows users to easily compare the
      // expected and actual output
      assertEquals(
          expected,
          output,
          String.format(
              "Your output and the expected output do not match. Writing your result into a file. %n"
                  + "You can compare the results by running the following command (red - expected, green - yours):%n"
                  + "git --no-pager diff --no-index --ignore-space-at-eol \"%s\" \"%s\" %n%n"
                  + "Afterwards, please delete the file by running (on a Linux machine):%n"
                  + "rm %s%n"
                  + "or (on a Windows machine):%n"
                  + "del /f %s",
              path.toAbsolutePath(),
              Paths.get(TESTER_WRONG_OUTPUT).toAbsolutePath(),
              Paths.get(TESTER_WRONG_OUTPUT).toAbsolutePath(),
              Paths.get(TESTER_WRONG_OUTPUT).toAbsolutePath()));
    }
  }
}
