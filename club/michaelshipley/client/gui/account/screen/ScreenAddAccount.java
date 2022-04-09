// 
// Decompiled by Procyon v0.5.30
// 

package club.michaelshipley.client.gui.account.screen;

import club.michaelshipley.client.account.AccountManager;
import club.michaelshipley.client.account.Alt;
import club.michaelshipley.client.gui.account.AccountScreen;
import club.michaelshipley.client.gui.account.component.Button;
import club.michaelshipley.client.gui.account.component.GuiPasswordField;
import club.michaelshipley.utils.ClientUtils;
import club.michaelshipley.utils.RenderUtils;
import club.michaelshipley.utils.minecraft.GuiTextField;

public class ScreenAddAccount extends Screen
{
    private AddButton addButton;
    private GuiTextField emailPassText;
    
    public ScreenAddAccount() {
        this.addButton = new AddButton();
        this.emailPassText = new GuiTextField(-5, ClientUtils.clientFont(), ClientUtils.mc().currentScreen.width / 2 - 60, ClientUtils.mc().currentScreen.height / 2 - 13 - 80, 120, 26);
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        if (!this.emailPassText.isFocused() && !this.emailPassText.getText().equals("§7Format: email:pass") && !this.emailPassText.getText().equals("§cCannot Be Blank") && this.emailPassText.getText().length() == 0) {
            this.emailPassText.setText("§7Format: email:pass");
        }
        RenderUtils.rectangle(0.0, 0.0, ClientUtils.mc().currentScreen.width, ClientUtils.mc().currentScreen.height, -804253680);
        this.addButton.draw(mouseX, mouseY);
        this.emailPassText.drawTextBox();
    }
    
    @Override
    public void onClick(final int x, final int y, final int mouseButton) {
        if (this.emailPassText.getText().equals("§7Format: email:pass") || this.emailPassText.getText().equals("§cCannot Be Blank")) {
            this.emailPassText.setText("");
        }
        if (this.addButton.isOver()) {
            this.addButton.onClick(mouseButton);
        }
        this.emailPassText.mouseClicked(x, y, mouseButton);
    }
    
    @Override
    public void onKeyPress(final char c, final int key) {
        this.emailPassText.textboxKeyTyped(c, key);
    }
    
    @Override
    public void update() {
        this.emailPassText.updateCursorCounter();
    }
    
    private class AddButton extends Button
    {
        public AddButton() {
            super("Add Account", ClientUtils.mc().currentScreen.width / 2 - 40, ClientUtils.mc().currentScreen.width / 2 + 40, ClientUtils.mc().currentScreen.height / 2 - 13 + 40, ClientUtils.mc().currentScreen.height / 2 + 13 + 40, -15921907, -16777216);
        }
        
        @Override
        public void onClick(final int button) {
            if (ScreenAddAccount.this.emailPassText.getText().length() == 0 || ScreenAddAccount.this.emailPassText.getText().equals("§7Format: email:pass") || ScreenAddAccount.this.emailPassText.getText().equals("§cCannot Be Blank")) {
                ScreenAddAccount.this.emailPassText.setText("§cCannot Be Blank");
                return;
            }
            AccountManager.addAlt(0, new Alt(ScreenAddAccount.this.emailPassText.getText().split(":")[0], ScreenAddAccount.this.emailPassText.getText().split(":")[0], ScreenAddAccount.this.emailPassText.getText().split(":")[1]));
            AccountScreen.getInstance().currentScreen = null;
            AccountManager.save();
            AccountScreen.getInstance().initGui();
            AccountScreen.getInstance().info = "§aAlt Added";
        }
    }
}
