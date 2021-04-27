public class Spell {

    // Creates spells and allows them to be cast
    private String spellName;
    private int manaCost;
    private int power;

    public Spell(String spellName, int manaCost, int strength) {
        this.spellName = spellName;
        this.manaCost = manaCost;
        this.power = strength;
    }

    public void castHarm(Enemy target, Player player) {
        target.setHealth(target.getHealth() - this.power);
        player.setMana(player.getMana() - this.manaCost);
    }

    public void castCurse(Enemy target, Player player) {
        target.setAttack(target.getAttack() - this.power);
        player.setMana(player.getMana() - this.manaCost);
        System.out.println("The enemy has been weakened!");
    }

    public void castHeal(Player player) {
        if (player.getHealth() + this.power > player.getMaxHealth()) {
            player.setHealth(player.getMaxHealth());
        }
        else player.setHealth(player.getHealth() + this.power);
        player.setMana(player.getMana() - this.manaCost);
    }

    public String getSpellName() {
        return spellName;
    }

    public void setSpellName(String spellName) {
        this.spellName = spellName;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
    }
}
