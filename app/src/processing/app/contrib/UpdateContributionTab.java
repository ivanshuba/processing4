package processing.app.contrib;

import java.awt.Color;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import processing.app.ui.Editor;
import processing.app.ui.Toolkit;


public class UpdateContributionTab extends ContributionTab {

  public UpdateContributionTab(ContributionType type,
                               ManagerFrame dialog) {
    super();
    this.contribType = type;
    this.contribDialog = dialog;

    filter = new Contribution.Filter() {
      public boolean matches(Contribution contrib) {
        if (contrib instanceof LocalContribution) {
          return ContributionListing.getInstance().hasUpdates(contrib);
        }
        return false;
      }
    };
    contributionListPanel = new UpdateListPanel(this, filter);
    statusPanel = new UpdateStatusPanel(this, 650, this);
    contribListing = ContributionListing.getInstance();
    contribListing.addListener(contributionListPanel);
  }


  @Override
  protected void setLayout(Editor editor, boolean activateErrorPanel,
                        boolean isLoading) {
    if (progressBar == null) {
      progressBar = new JProgressBar();
      progressBar.setVisible(false);

      buildErrorPanel();

      loaderLabel = new JLabel(Toolkit.getLibIcon("icons/loader.gif"));
      loaderLabel.setOpaque(false);
      loaderLabel.setBackground(Color.WHITE);
    }

    GroupLayout layout = new GroupLayout(this);
    setLayout(layout);
    layout.setHorizontalGroup(layout
      .createParallelGroup(GroupLayout.Alignment.CENTER)
      .addComponent(loaderLabel)
      .addComponent(contributionListPanel).addComponent(errorPanel)
      .addComponent(statusPanel));

    layout.setVerticalGroup(layout
      .createSequentialGroup()
      .addGap(2)
      .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                  .addComponent(loaderLabel)
                  .addComponent(contributionListPanel))
      .addComponent(errorPanel)
      .addComponent(statusPanel, GroupLayout.PREFERRED_SIZE,
                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE));
    layout.setHonorsVisibility(contributionListPanel, false);

    setBackground(Color.WHITE);
  }
}
